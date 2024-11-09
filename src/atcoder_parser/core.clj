(ns atcoder-parser.core
  (:require
   [babashka.http-client :as http]
   [clojure.string :as str]
   [clojure.tools.logging :as log]
   [hickory.core :as hickory]
   [hickory.select :as hickory.select])
  (:gen-class))

(defn text-content
  ([obj]
   (letfn [(f [x] (if (map? x)
                    (apply str (map f (:content x)))
                    x))]
     (text-content obj f)))
  ([obj f]
   (-> (f obj)
       (str/replace #"\r\n" "\n")
       str/trim)))

(defn fetch-page [url]
  (-> url
      http/get
      :body
      hickory/parse
      hickory/as-hickory))

(defn parse-task-urls [obj]
  (->> obj
       (hickory.select/select
        (hickory.select/descendant
         (hickory.select/and
          (hickory.select/tag :td)
          (hickory.select/class :text-center))
         (hickory.select/tag :a)))
       (map (comp :href :attrs))
       (map #(str "https://atcoder.jp" %))))

(defn parse-task-contents [obj]
  (->> obj
       (hickory.select/select
        (hickory.select/descendant
         (hickory.select/id :task-statement)
         (hickory.select/class :lang-ja)
         (hickory.select/tag :section)))
       (map #(hash-map
              :name
              (->> %
                   (hickory.select/select
                    (hickory.select/child
                     (hickory.select/tag :h3)
                     (hickory.select/child)))
                   first)
              :contents
              (letfn [(f [x] (cond
                               (= :h3 (:tag x)) nil
                               (map? x) (apply str (map f (:content x)))
                               :else x))]
                (text-content % f))))))

(defn parse-task-limit [obj]
  (->> obj
       (hickory.select/select
        (hickory.select/tag :p))
       (keep #(as-> (text-content %) c
                (when (str/includes? c "Time Limit") c)))
       first
       (re-find #"Time Limit:\s*(\d+)\s*sec\s*/\s*Memory Limit:\s*(\d+)\s*MB")
       (#(hash-map :time-limit (parse-long (nth % 1))
                   :memory-limit (parse-long (nth % 2))))))

(defn parse-task-page [obj]
  (let [[contents limit] ((juxt parse-task-contents parse-task-limit) obj)]
    (assoc limit :contents contents)))

(defn -main
  "The entrypoint."
  [& args]
  (log/info args))

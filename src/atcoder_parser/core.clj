(ns atcoder-parser.core
  (:require
   [babashka.http-client :as http]
   [clojure.string :as str]
   [clojure.tools.logging :as log]
   [hickory.core :as hickory]
   [hickory.select :as hickory.select])
  (:gen-class))

(defn fetch-page [url]
  (-> url
      http/get
      :body
      hickory/parse
      hickory/as-hickory))

(defn get-task-urls [obj]
  (->> obj
       (hickory.select/select
        (hickory.select/descendant
         (hickory.select/and
          (hickory.select/tag :td)
          (hickory.select/class :text-center))
         (hickory.select/tag :a)))
       (map (comp :href :attrs))
       (map #(str "https://atcoder.jp" %))))

(defn parse-task-page [obj]
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
                (-> (f %)
                    (str/replace #"\r\n" "\n")
                    str/trim))))))

(defn -main
  "The entrypoint."
  [& args]
  (log/info args))

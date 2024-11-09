# clojure-atcoder-parser

```clojure
(-> (fetch-page "https://atcoder.jp/contests/abc378/tasks")
    get-task-urls)
;;=> ("https://atcoder.jp/contests/abc378/tasks/abc378_a"
;;    "https://atcoder.jp/contests/abc378/tasks/abc378_b"
;;    "https://atcoder.jp/contests/abc378/tasks/abc378_c"
;;    "https://atcoder.jp/contests/abc378/tasks/abc378_d"
;;    "https://atcoder.jp/contests/abc378/tasks/abc378_e"
;;    "https://atcoder.jp/contests/abc378/tasks/abc378_f"
;;    "https://atcoder.jp/contests/abc378/tasks/abc378_g")

(-> (fetch-page "https://atcoder.jp/contests/abc378/tasks/abc378_a")
    parse-task-page)
;;=> ({:name "問題文", :contents "4 個のボールがあり、i 個目のボールの色は A_i です。\n同じ色のボールを 2 つ選び..."}
;;    {:name "制約", :contents "A_1,A_2,A_3,A_4 はそれぞれ 1 以上 4 以下の整数"}
;;    {:name "入力", :contents "入力は以下の形式で標準入力から与えられる。\nA_1 A_2 A_3 A_4"}
;;    {:name "出力", :contents "操作回数の最大値を整数として出力せよ。"}
;;    {:name "入力例 1", :contents "2 1 2 1"}
;;    {:name "出力例 1", :contents "2\n\n1 個目のボールと 3 個目のボールはどちらも色が 2 なので、1 個目のボール..."}
;;    {:name "入力例 2", :contents "4 4 4 1"}
;;    {:name "出力例 2", :contents "1"}
;;    {:name "入力例 3", :contents "1 2 3 4"}
;;    {:name "出力例 3", :contents "0\n\n操作を一度も行えない場合もあります。"})
```

# clojure-atcoder-parser

A Clojure library for parsing AtCoder contest pages. Extracts task URLs, problem statements, constraints, and test cases from AtCoder's HTML pages.

## Features

- Fetch and parse AtCoder contest task lists
- Extract individual task details including time/memory limits
- Parse problem statements, constraints, and sample I/O

## Requirements

- Clojure 1.12+
- Clojure CLI (deps.edn)

## Installation

Add to your `deps.edn`:

```clojure
{:deps
 {io.github.conao3/clojure-atcoder-parser {:git/tag "v0.1.0" :git/sha "xxxxxxx"}}}
```

## Usage

### Fetching Task URLs from a Contest

```clojure
(require '[atcoder-parser.core :refer [fetch-page parse-task-urls]])

(-> (fetch-page "https://atcoder.jp/contests/abc378/tasks" {})
    parse-task-urls)
;; => ("https://atcoder.jp/contests/abc378/tasks/abc378_a"
;;     "https://atcoder.jp/contests/abc378/tasks/abc378_b"
;;     "https://atcoder.jp/contests/abc378/tasks/abc378_c"
;;     "https://atcoder.jp/contests/abc378/tasks/abc378_d"
;;     "https://atcoder.jp/contests/abc378/tasks/abc378_e"
;;     "https://atcoder.jp/contests/abc378/tasks/abc378_f"
;;     "https://atcoder.jp/contests/abc378/tasks/abc378_g")
```

### Parsing a Task Page

```clojure
(require '[atcoder-parser.core :refer [fetch-page parse-task-page]])

(->> (fetch-page "https://atcoder.jp/contests/abc378/tasks/abc378_a" {})
     parse-task-page)
;; => {:time-limit 2
;;     :memory-limit 1024
;;     :contents ({:name "Problem Statement" :contents "..."}
;;                {:name "Constraints" :contents "..."}
;;                {:name "Input" :contents "..."}
;;                {:name "Output" :contents "..."}
;;                {:name "Sample Input 1" :contents "..."}
;;                {:name "Sample Output 1" :contents "..."})}
```

## API Reference

### `fetch-page`

```clojure
(fetch-page url opts)
```

Fetches a URL and parses the HTML into a Hickory data structure. The `opts` parameter is passed directly to the HTTP client.

### `parse-task-urls`

```clojure
(parse-task-urls hickory-obj)
```

Extracts task URLs from a contest tasks page.

### `parse-task-page`

```clojure
(parse-task-page hickory-obj)
```

Parses a task page and returns a map containing:
- `:time-limit` - Time limit in seconds
- `:memory-limit` - Memory limit in MB
- `:contents` - List of sections (problem statement, constraints, I/O examples)

## Development

```bash
# Run tests
clojure -M:test

# Start REPL with dev alias
clojure -M:dev
```

## License

This project is open source.

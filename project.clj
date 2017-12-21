(defproject clojure-intro "0.0.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [cheshire "5.7.1"]
                 [clj-http "3.7.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]]

  :aot [clojure-intro.main]

  :main clojure-intro.main)

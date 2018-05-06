(ns clojure-intro.main
  (:require [clojure-intro.http-server :as web-app])
  (:gen-class))

(defn -main [ & args]
  (println "Starting..." args)
  (web-app/start-server)
  (println "Startup Completed."))

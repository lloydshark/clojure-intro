(ns clojure-intro.main
  (:require [clojure-intro.http-server :as http-server])
  (:gen-class))

(defn -main [ & args]
  (println "Starting..." args)
  (http-server/start-server)
  (println "Startup Completed."))


;; lein uberjar
;;
;; (main above is defined via project.clj)
;;
;; cd target
;;
;; java -jar clojure-intro-XYZ-standalone.jar
;;
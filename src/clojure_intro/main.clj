(ns clojure-intro.main
  (:require [clojure-intro.part2 :as part2])
  (:gen-class))

(defn -main [ & args]
  (println "Starting..." args)
  (part2/start-server)
  (println "Startup Completed."))


;; lein uberjar
;;
;; (main above is defined via project.clj)
;;
;; cd target
;;
;; java -jar clojure-intro-XYZ-standalone.jar
;;
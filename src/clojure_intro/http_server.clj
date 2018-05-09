(ns clojure-intro.http-server
  (:require [ring.adapter.jetty :as jetty]
            [cheshire.core :as json]
            [clojure-intro.example :as example]))


(defn list-people-request-handler [_]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (json/generate-string (example/list-people))})

(defn not-found-request-handler [_]
  {:status 404})

(def routes
  {
   "/list-people" list-people-request-handler
   })

(defn find-request-handler [request]
  (get routes (:uri request) not-found-request-handler))

(defn handler [request]
  (println request) ;; It's just a Map...
  ((find-request-handler request) request))

(defn start-server []
  (jetty/run-jetty #(handler %)
                   {:port  3000
                    :join? false}))

(comment

  (def my-server (start-server))

  (example/add-person example/mary)

  (example/list-people)

  (.stop my-server)

  )


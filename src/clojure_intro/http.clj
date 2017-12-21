(ns clojure-intro.http
  (:require [cheshire.core :as json]
            [clj-http.client :as client]))


(defn fetch-people []
  (let [response (client/get "http://localhost:3000/list-people")]
    (json/parse-string (:body response) true)))



(comment

  (client/get "http://localhost:3000/list-people")

  (map :name (fetch-people))


  )

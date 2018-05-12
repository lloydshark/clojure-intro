(ns clojure-intro.part2
  (:require [cheshire.core :as json]
            [clojure-intro.part1 :as part1]
            [clj-http.client :as client]
            [ring.adapter.jetty :as jetty])
  (:import (com.amazonaws.services.s3 AmazonS3ClientBuilder AmazonS3)
           (com.amazonaws.services.s3.model S3ObjectSummary)))


;; ---------------------------------------------------------------------------------------------------------------
;; >> Polymorphism et al.
;;
;; defmulti, defprotocol, defrecord, refify.
;;
;; What am I learning?
;;
;; - You can still have polymorphic dispatch. But disconnected from Object hierarchies.
;;

(defmulti notification
          (fn [account]
            (if (> 0 (:balance account)) :overdrawn :normal)))

(defmethod notification :overdrawn [account]
  (format "Your account is overdrawn by %s." (:balance account)))

(defmethod notification :normal [account]
  (format "Greetings %s, welcome to TheBank!" (:name account)))

(notification {:name    "John Smith"
               :balance -123})


;; defrecord / deftype / defprotcol

(defprotocol Animal

  (number-of-legs [animal])

  (speak [animal]))

(defrecord Dog [dog-tag owner] Animal

  (number-of-legs [animal] 4)

  (speak [animal] "Woof!"))

(def a-dog (->Dog "12345" "John"))

(speak a-dog)

(def anonymous-animal

  (reify Animal

    (number-of-legs [animal] 436)

    (speak [animal] "Bluurgen!")))

(speak anonymous-animal)


;; Learnings?
;;
;; - Most relevant say where you might have a real database implementation,
;;   but wish to insert a Dummy implementation for unit / integration testing purposes.


;; ---------------------------------------------------------------------------------------------------
;; >> Build a simple Web Server.
;;

(defn list-people-request-handler [_]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (json/generate-string (part1/list-names))})

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


;; ---------------------------------------------------------------------------------------------------
;; >> Build a simple http client to access the above web server.
;;


(defn fetch-people []
  (let [response (client/get "http://localhost:3000/list-people")]
    (json/parse-string (:body response) true)))


(comment

  (client/get "http://localhost:3000/list-people")

  (fetch-people)

  (map :name (fetch-people))

  )


;; ---------------------------------------------------------------------------------------------------
;; >> Use the AWS Java SDK to access S3.
;;

(defn new-s3-client []
  (AmazonS3ClientBuilder/defaultClient))

(defn s3-object-data [^S3ObjectSummary summary]
  {:key    (.getKey summary)
   :bucket (.getBucketName summary)
   :size   (.getSize summary)})


(defn list-s3-objects [^AmazonS3 s3-client ^String bucket-name]
  (->> (.listObjects s3-client bucket-name)
       .getObjectSummaries
       (map s3-object-data)
       (sort-by :size)))


(comment

  (def s3-client (new-s3-client))

  (def list-objects (.listObjects ^AmazonS3 s3-client "s3-bucket-name-here"))

  (list-s3-objects s3-client "s3-bucket-name-here")

  )
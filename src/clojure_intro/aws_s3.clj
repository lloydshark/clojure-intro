(ns clojure-intro.aws-s3
  (:import (com.amazonaws.services.s3 AmazonS3ClientBuilder AmazonS3)
           (com.amazonaws.services.s3.model S3ObjectSummary)))


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
(ns clojure-intro.core
  (:import (java.util Date UUID)))

;; Clojure - Sameness everywhere.
;;
;; "A language that doesn't affect the way you think about programming, is not worth knowing." - Alan Perlis.
;;
;; Learning: Clojure not wacky, not academic - I could build something in that.
;;
;; LISP second oldest programming language. Wikipedia says 1958.
;; As such many of the concepts in computer science started in LISP.
;;
;; Clojure - A general purpose language with a emphasis of simplicity.
;;
;; Clojure 2007 - on the JVM. ClojureScript 2010 - a js cross compiler.
;;
;; Rich Hickey describes it as an opinionated language. That's hard to pin down, but I think
;; it refers to the immutable data structures and some of the core library semantics.
;;


;; Basic Data types / Data structures / Syntax... Immutable.
;;
;; Learning: There's a simple readable syntax for defining the data structures of Clojure.
;;           And there is a REPL for you to explore them. Read Eval Print Loop.
;;           Everything needs to be defined in lexical order. ie Can't refer to something
;;           That doesn't exist yet.

;; number
27

;; float
1.234

;; boolean
true
false

;; character
\z

;; nil / null / no value
nil

;; string
"the quick brown fox"

;; keyword (symbolic identifier, fast lookups)
:banana
(keyword "banana")

;; symbol (often not created explicitly).
'fred
(symbol "fred")

;; A list
(list 1 2 3 4)
'(1 2 3 4)

;; vector
(vector 1 2 3 4)
[1 2 3 4]

;; set
#{1 2 3 4}

;; Map
{:name "fred"
 :age  27}

{:name "fred"
 :age  27
 :star-sign "taurus"}

;; Any key or value
{1        "fred"
 :this    nil
 "my-key" 234M}

;; Maps in Maps
{:name "fred"
 :age  27
 :children [{:name "Sam"
             :age   6}]}



;; "Global" Variables (values) in this namespace
;;
;;

(def fruit ["apple" "orange" "banana" "kettle"])

(def a-person {:name "jack"
               :age 43})


;; Defining Functions...
;;
;; Always returns a value (the last value of the function).
;; Yes the brackets have to match. :)
;;
;; Learning: They're just the same (lists / vectors etc),
;; no new sytax to learn.
;;

;; Normal...
(defn add-one [number]
  (+ 1 number))

(defn add-two-numbers [one two & rest]
  (+ one two))



;; Anonymous...
(fn [number] (+ 1 number))

;; Super Compact Anonymous version.
#(+ 1 %)


;; Calling Functions...
;;
;; function name is first in the list.
;;
;; Learning: Just move the bracket to the left :)
;;

(add-one 2)


;; Identity, Values. Immutable.
;;
;; Learning: Persistent Data structures.
;;
;; No more writing toString or hashCode. Equals is Equals.
;; Functions operate on pure printable data.
;; When debugging you can worry about pure immutable printable.
;; Tests care about pure printable data.
;; No data is "changed" underneath you.
;;

(def fred {:name "fred"
           :age  27
           :star-sign "taurus"})

(def mary {:name "mary"
           :age  38
           :star-sign "leo"})

(def steven {:name "steven"
             :age  12
             :star-sign "capricorn"})

(def twin {:name "steven"
             :age  12
             :star-sign "capricorn"})

(def carol {:name "carol"
            :age  15
            :star-sign "taurus"})


;; Access / "Change" a List...
(def all-people (list fred mary steven))

(first all-people)

(conj all-people carol)

all-people


;; Access / "Change" a Map
(get fred :age)

(:age fred)

(assoc fred :age 50)

fred

(dissoc fred :age)

fred


;; Analogy Flow vs Message Sending...
;;
;; The thing is in the OO world, I became very used to modeling the world as Objects
;; and then seeing my application as a series of messages between objects.
;; The bad version is gunfight at the ok corral.
;;
;; With immutable data in a functional world, I now see the code more like a
;; river flowing.  ie It starts somewhere and transforms along the way.



;; Now that we know how to call a function - lets try out some of the core data structures
;; and core functions.
;; Learning - Sameness Everywhere for sequences. No need to learn different functions.
;;

(def fruit-list (list "apple" "orange" "banana" "kettle"))

;; List...
(first fruit-list)
(rest fruit-list)
(take 2 fruit-list)
(nth fruit-list 3)
(count fruit-list)

(def fruit-vector ["apple" "orange" "banana" "kettle"])

;; Vector...
(first fruit-vector)
(rest fruit-vector)
(take 2 fruit-vector)
(nth fruit-vector 3)
(count fruit-vector)

(def a-fruit {:name        "banana"
              :color       "yellow"
              :picked-time 1513736101854
              :source      :QUEENSLAND})

;; And maps as well !
(first a-fruit)
(rest a-fruit)
(take 2 a-fruit)
(count a-fruit)

;; And Strings !
(first "banana")
(rest "banana")
(take 2 "banana")
(nth "banana" 3)
(count "banana")



;; Basic Algorithms / Logic / Flow of Control...

(defn adult? [person]
  (<= 18 (:age person)))

(defn describe-person [person]
  (if (adult? person)
    (format "%s is an adult." (:name person))
    (format "%s is a child." (:name person))))

(defn add-age [total person]
  (+ total (:age person)))


(comment

  (describe-person fred)

  ;; Find the adults...
  (filter adult? all-people)

  ;; Calculator the total ages...
  (reduce add-age 0 all-people)

  ;; Find all the names....
  (map :name all-people)

  )


;; Readability / nil safety / truthy...
;;
;; Learning: Less Code, less fragility.

(map :name nil)

(filter adult? nil)

(get-in nil [:age :banana])

(get nil :name)

(assoc-in nil [:this :that] 57)

(if nil "truthy" "falsey")
(if false "truthy" "falsey")
(if nil "truthy" "falsey")
(if 6 "truthy" "falsey")

(and 4 6)


;; Nice Libraries...
;;
;; Learning: Nice abstractions means compact code. Low cruft.
;; (and I don't mean cryptic compact).
;;
;; Less code -> Less errors ... Can we say that?
;;

(defn parse-person [line]
  (let [columns (clojure.string/split line #",")]
    {:name      (nth columns 0)
     :age       (nth columns 1)
     :star-sign (nth columns 2)})
  ;(zipmap (list :name :age :star-sign) (clojure.string/split line #","))
  )

(defn read-people-from-file [file-path]
  (with-open [rdr (clojure.java.io/reader file-path)]
    (doall (map parse-person (line-seq rdr)))))

(defn star-sign-histogram [people]
  (->> (sort-by :star-sign people)
       (partition-by :star-sign)
       (map #(vector (:star-sign (first %)) (count %)))))

(defn output-star-sign-histogram [histogram file-path]
  (doseq [[star-sign frequency] histogram]
    (println star-sign)
    (spit file-path (format "%s,%s\n" star-sign frequency)
          :append true)))

(comment

  (def people-from-file (read-people-from-file "resources/people.txt"))

  people-from-file

  (def histogram (star-sign-histogram people-from-file))

  histogram

  (output-star-sign-histogram histogram "out/histogram.csv")

  )

;;
;; Learning:  What is our job if not often to take one form of data and transform it
;; to another in order to achieve the business function.
;; Here we have immutable data (values) and a range of functions that facilitate
;; that manipulation with (hopefully) very few movements.
;; Some refer to Clojure as a data orientated language and that it is specially suited
;; for "data" problems.
;;
;; But aren't all our problems "data" problems !



;; Java Interop

;; Special Syntax for creating a new instance...
(def current-date (Date.))

;; Method calling method starts with dot.
(.getTime current-date)

;; Static Method...
(System/currentTimeMillis)

(-> (UUID/randomUUID) .toString)



;; But what if I do have some state...
;;
;; Learning: Simple concurrency is straight forward (STM).
;;

(defonce people-by-id (atom {}))

(defn add-person [person]
  (let [id (.toString (UUID/randomUUID))]
    (swap! people-by-id
           (fn [people]
             (assoc people id (assoc person :id id))))))

(defn list-people []
  (vals @people-by-id))

(defn list-names []
  (map :name (list-people)))

(defn clear-people []
  (reset! people-by-id {}))

(comment

  people-by-id

  @people-by-id

  (add-person mary)

  (list-people)

  (list-names)

  (clear-people)

  )



;; Polymorphism et al
;;
;; defmulti, defprotocol, defrecord, refify.
;;
;; Learning: You can still have polymorphic dispatch. But disconnected from Object heirarchies.
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


;; Learnings: Most relevant say where you might have a real database implementation,
;; but wish to insert a Dummy implementation for unit / integration testing purposes.


;; Macros...
;;
;; Maliability of the language. You can easily extend the language if it suits.
;;
;; But nothing new here the macro works on manipulating existing Clojure data structures.
;;

(comment

  (when (adult? fred)
    (println "++++++++")
    (println (describe-person fred))
    (println "++++++++"))

  )


;(macroexpand-1
;  '(when (adult? fred)
;     (println "++++++++")
;     (println (describe-person fred))
;     (println "++++++++")))
;
;(if (adult? fred)
;  (do (println "++++++++")
;      (println (describe-person fred))
;      (println "++++++++")))



;; So what are the downsides...
;;
;;
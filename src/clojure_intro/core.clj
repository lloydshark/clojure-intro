(ns clojure-intro.core
  (:import (java.util Date UUID)))

;; ----------------------------------------------------------------------------------------------------------------
;; Clojure - "Sameness everywhere".
;;
;; "A language that doesn't affect the way you think about programming, is not worth knowing." - Alan Perlis.
;;
;; What am I Learning? Clojure is not wacky, not academic - come away thinking "yes, I could build something in that".
;;
;; Context:
;;
;; LISP second oldest programming language. Wikipedia says 1958.
;; As such many of the concepts in computer science started in LISP.
;;
;; Clojure - A general purpose language with a emphasis (and culture) of simplicity.
;;
;; Clojure 2007 - on the JVM. ClojureScript 2010 - a javascript cross compiler.
;;
;; Rich Hickey (the Clojure inventor) describes it as an opinionated language. That's hard to pin down, but I think
;; it refers to the immutable data structures and some of the core library semantics.
;;
;;

;; ----------------------------------------------------------------------------------------------------------------
;; 0. Getting Started.
;;
;; What am I learning? It's not complicated to setup.
;;                     And if you are a Java developer its really not a big step.
;;
;; https://clojure.org/
;;
;; On Mac:
;; brew install clojure
;;
;; Linux:
;; curl -O https://download.clojure.org/install/linux-install-1.9.0.375.sh
;;
;; This talk is presented using IntelliJ plus Cursive plugin.
;;
;; And this project itself uses the leiningen project / build tools.
;;
;; ----------------------------------------------------------------------------------------------------------------


;; ----------------------------------------------------------------------------------------------------------------
;; 1. Basic Data types / Data structures.
;;
;; What am I Learning? There's a simple readable syntax for defining the data structures of Clojure.
;;                     And there is a REPL for you to explore them. Read Eval Print Loop.
;;                     Everything needs to be defined in lexical order.
;;                     ie Can't refer to something that doesn't exist yet.
;; ----------------------------------------------------------------------------------------------------------------


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

;; symbol (typically not created explicitly, we'll talk about it later).
'banana

;; A list
'(1 2 3 4)

;; vector
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

;; Regular Expression
#"^The (.+) brown fox$"


;; ----------------------------------------------------------------------------------------------------------------
;; 2. "Global" Variables (values) in this namespace.
;;
;; What am I learning?
;;
;; - Convention, kebab-case for names rather than say Camel.
;; - This is analogous to "public static final" in a Java Class.
;; ----------------------------------------------------------------------------------------------------------------

;; A Vector of fruit names.
(def fruit ["apple" "orange" "banana" "pineapple"])

;; A Map ie Thing with properties & values. In Java say a DTO / simple bean.
(def example-person {:name "jack"
                     :age  43})


;; ----------------------------------------------------------------------------------------------------------------
;; 3. Calling Functions...
;;
;; Function name is first in the list, then the parameters (Polish Notation).
;;
;; What am I learning?
;;
;; - Don't be afraid, just move the bracket to the left :)
;;

(+ 1 2) ;; 1 + 2

(first fruit) ;; fruit.first()

(get example-person :name)  ;; example-person.get(:name)



;; ----------------------------------------------------------------------------------------------------------------
;; 4. Define our own Functions...
;;
;; They always returns a value (the last value of the function). No explicit "return" statement.
;; Yes the brackets have to match. :)
;;
;; What am I Learning? They're just the same (lists / vectors etc),
;;                     no new syntax to learn.
;;                     Zero magic, where possible pure functions.
;;

;; Normal... One parameter function.
(defn add-one [number]
  (+ 1 number))

;; Anonymous...
(fn [number] (+ 1 number))

;; Super Compact Anonymous version...
#(+ 1 %)


(add-one 2) ;; 3

;; Multiple Parameters...
(defn add-numbers [first-number second-number]
  (+ first-number second-number))

(add-numbers 1 2) ;; 3

;; Variable Parameters...
(defn add-lots-of-numbers [first-number second-number & other-numbers]
  (+ first-number second-number (apply + other-numbers)))

(add-lots-of-numbers 10 2 3 15) ;; 30



;; ----------------------------------------------------------------------------------------------------------------
;; 5. Identity, Values. Immutability.
;;
;; What am I Learning? Persistent Data structures.
;;
;; - No more writing toString, hashCode, clone, deepEquals EQUALS is EQUALS is "="
;; - Your functions operate on pure immutable printable data.
;; - When debugging you only need to worry about pure immutable printable data.
;; - Your tests only need to care about pure immutable printable data.
;; - No data is "changed" underneath you.
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

;; Equals is Equals, same data means equals is true.

(= steven twin) ;; true


;; Access / "Change" a List...
(def all-people (list fred mary steven))

(first all-people)

(conj all-people carol)

;; The original list didn't change.
all-people


;; Access / "Change" a Map
(get fred :age)

(:age fred)

(assoc fred :age 50)

fred

(dissoc fred :age)

;; Original fred didn't change.
fred


;; Discussion: Analogy "Flow" vs "Message Sending"...
;;
;; In the Object Oriented world, I became very used to modeling the world as Objects
;; and then seeing my application as a series of messages between objects.
;; I became very good at it and thought this was THE way.
;;
;; But the bad version is gunfight at the ok corral. With messages (bullets flying everywhere).
;;
;; With immutable data in a functional world, I now see the code more like a
;; river flowing.  ie It starts somewhere and transforms along the way.
;;



;; ----------------------------------------------------------------------------------------------------------------
;; 6. Now that we know how to call a function - lets try out some of the core data structures
;; and core functions.

;; What am I Learning: Sameness Everywhere for sequences. No need to learn different functions for different behaviour.
;;

(def fruit-list (list "apple" "orange" "banana" "pineapple"))

;; List...
(first fruit-list)
(rest fruit-list)
(take 2 fruit-list)
(nth fruit-list 3)
(count fruit-list)

(def fruit-vector ["apple" "orange" "banana" "pineapple"])

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



;; ----------------------------------------------------------------------------------------------------------------
;; 7. Basic Algorithms...
;;
;; The question mark here is just a convention - it has no special significance.
;;
;; Basic Logic If Else
;; Map / Filter / Reduce
;; Cond / Switch

(defn adult? [person]
  (<= 18 (:age person)))

(defn describe-person [person]
  (if (adult? person)
    (format "%s is an adult." (:name person))
    (format "%s is a child." (:name person))))

(comment

  (describe-person fred)

  ;; Find the adults...
  (filter adult? all-people)

  ;; Find all the names....
  (map :name all-people)

  ;; Sum the total age of all the people...
  (reduce (fn [total person] (+ total (:age person)))
          0
          all-people
          )

  )


;; ----------------------------------------------------------------------------------------------------------------
;; 8. Readability / nil safety / truthy...
;;
;; What am I learning?
;;
;;- Less Code, less fragility.
;;

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


;; ----------------------------------------------------------------------------------------------------------------
;; 9. Nice Libraries...
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

;; What am I learning:  What is our job if not often to take one form of data and transform it
;; to another in order to achieve the business function.
;; Here we have immutable data (values) and a range of functions that facilitate
;; that manipulation with (hopefully) very few movements.
;; Some refer to Clojure as a data orientated language and that it is specially suited
;; for "data" problems.
;;
;; But aren't all our problems "data" problems !



;; ---------------------------------------------------------------------------------------------------------------
;; 10. Java Interop
;;
;; What am I learning?
;;
;; - Yes, you can still rely on well known / battle tested Java libraries.
;;


;; Special Syntax for creating a new instance...
(def current-date (Date.))

;; Method calling method starts with dot.
(.getTime current-date)

;; Static Method...
(System/currentTimeMillis)

(.toString (UUID/randomUUID))


;; ---------------------------------------------------------------------------------------------------------------
;; 11. But what if I do have some state.
;;
;; In clojure one of the main mechanisms for managing state in a controlled way is called and atom.
;;
;; What am I learning?
;;
;; - If you do have a need for "mutable" state - then there is a mechanism for this.
;; - Basic concurrency is straight forward.
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


;; ---------------------------------------------------------------------------------------------------------------
;; 12. Polymorphism et al
;;
;; defmulti, defprotocol, defrecord, refify.
;;
;; What am I learning: You can still have polymorphic dispatch. But disconnected from Object hierarchies.
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


;; ---------------------------------------------------------------------------------------------------------------
;; 13. Macros...
;;
;; Malleability of the language. You can easily extend the language if it suits.
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
;
; You'd think "when" is something in the language - in actual fact it is just a macro which at "read"
; time is converted to "if" and "do".
;
; What am I learning? That you can extend the language as you see fit if you want to and provide new
; features if it makes sense for your application.



;; So what are the downsides...
;;
;; - No dominant frameworks.
;; - Some styles of problems perhaps it doesn't suit.
;; - FUD
;; - Smaller Community
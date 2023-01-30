(ns app
  (:require ;; ["moment$default" :as mmt]
   ["inquirer$default" :as inq]
   [promesa.core :as p]
   ["ink" :refer [render Text Box]]
   [reagent.core :as r]
   ;; [nbb.core :refer [await]]
   [share.lib :as l]
   ;; [cljs.core.async :refer [go]]
   ;; [cljs.core.async.interop :refer-macros [<p!]]
   ))

;; TODO: auto send commands to vterm to run nbb app.
(def state
  (r/atom
   {:dev {:check-roundtrip true
          :check-roundtrip-interval-sec 60
          :ms {:rs-ok true
               :pr-ok true
               :pns-ok true
               :es-ok true}}

    :stg {:check-roundtrip true
          :check-roundtrip-interval-sec 60
          :ms {:rs-ok true
               :pr-ok false
               :pns-ok true
               :es-ok true}}

    :prod {:check-roundtrip true
           :check-roundtrip-interval-sec 60
           :ms {:rs-ok true
                :pr-ok true
                :pns-ok true
                :es-ok true}}}))
(-> @state :stg :ms)




(defn ok-status [env]
  (let [e (-> @state env :ms)]
    ^{:key env}
    [:> Box
     [:> Box {:width 5}
      [:> Text env]]
     [:> Box
      (if (every? #(= true (val %)) e)
        [:> Text {:color "green"} "Everything is good."]
        [:> Text {:color "red"} "FAILED!!!"]
        )]])
  )
(defn main []
  [:> Box {:flexDirection "column"}
   [:> Box
    [:> Box {:width 5} [:> Text {:color "blue"} "ENV"]]
    [:> Box [:> Text {:color "blue"} "OK Status"]]]
   (map ok-status [:dev :stg :prod])])

(render (r/as-element [main]))

(comment
  (def questions (clj->js [ ;; {:name "name"
                           ;;  :type "input"
                           ;;  :message "who are you?"}
                           ;; {:name "main-menu"
                           ;;  :choices [{:name "a" :value "A"}
                           ;;            {:name "b" :value "B"}
                           ;;            {:name "q" :value "q"}]
                           ;;  :type "checkbox"
                           ;;  :message "who are you?"}
                           {:name "main-menu"
                            :choices ["quit" "deploy" "health-check"]
                            :type "list"
                            :message "who are you?"}]))

  (defn prompt-user []
    (p/let [_answers (inq/prompt questions)
            answers (js->clj _answers :keywordize-keys true)]
      (swap! state assoc :prompting-user true)
      (case (-> answers :main-menu)
        "quit" (swap! state assoc :quit true) 
        "deploy" (swap! state assoc :deploy true))))


  (while (-> @state :quit not)
    (await
     (prompt-user)
     (prn @state)))

  ;; (prompt-user)
  ;; (p/let [_answers (inq/prompt questions)
  ;;         answers (js->clj _answers :keywordize-keys true)]
  ;;   (case (-> answers :main-menu)
  ;;     "quit" (swap! state assoc :quit true) 
  ;;     "deploy" (swap! state assoc :deploy true)
  ;;     (swap! state assoc :invalid true))
  ;;   (println @state))
  ;; (println "hello")

  ;; {:main-menu "deploy"}
  ;; {:alphabet ["B" "C"]}
  )





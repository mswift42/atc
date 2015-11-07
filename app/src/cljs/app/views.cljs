(ns app.views
  (:require [re-frame.core :as re-frame]))

;; --------------------
(defn color-component
  "color-component returns a div, 
   with the themeface name, a preview span element,
   and a facecolor input."
  [facename facecolor ]
  [:div.colors.mdl-grid
   [:label.mdl-cell.mdl--4-col (str facename)]
   [:span.colorpreview.mdl-cell.mdl--2-col
    {:style {:background-color facecolor}}]
   [:input.mdl-cell.mdl--4-col {:type "text"}
    ]])




(defn home-panel []
  (let [face (re-frame/subscribe [:theme])]
    (fn []
      [:div (str "Hello from " (:mainbg @face) ". This is the Home Page.")
       [color-component "mainbg" (:mainfg @face)]
       [color-component "comment" (:comment @face)]
       [:div [:a {:href "#/about"} "go to About Page"]]]
      ;; (color-component "mainbg" [:mainbg])
      
      )))

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "#/"} "go to Home Page"]]]))

;; --------------------
(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :about-panel [] [about-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      (panels @active-panel))))

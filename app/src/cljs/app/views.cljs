(ns app.views
  (:require [re-frame.core :as re-frame]))

;; --------------------
(defn color-component
  "color-component returns a div, 
   with the themeface name, a preview span element,
   and a facecolor input."
  [facename facecolor]
  (let [color (re-frame/subscribe facecolor)]
    (fn []
      [:div.colors.row
       [:label.col-xs-6.offset-1 (str facename)]
       [:span.colorpreview.col-xs-1.offset-1
        {:style {:color @color}}]
       [:input.col-xs-3.offset-0.pull-right
        {:required "" :colorpicker ""}]])))

(defn home-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div (str "Hello from " @name ". This is the Home Page.")
       [:div [:a {:href "#/about"} "go to About Page"]]])))

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

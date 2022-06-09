### DAY 1



### DAY 2



### DAY 3 : 機器學習
+ Scikit-learn: machine learning in python
    - https://scikit-learn.org/stable/

+ dmlc XGBoost 
    - https://xgboost.readthedocs.io/en/stable/

+ 機器學習 Machine Learning
    - 是關於從資料中擷取出知識。是統計、人工智慧和電腦科學交集而成。又稱為預測分析 Predictive Analytics、統計學習。

+ DT (決策樹演算法 descision tree) : 符合一個特徵的分左邊，不符合的分右邊

+ sklearn (是 scikit-learn 在 python 中的縮寫)

+ 玩具資料集(toy dataset) : 雖然真實但是是少量的

+ 監督式 (POINT of TODAY) supervised learning
+ 非監督式 unsupervised learning
+ 增強式機器學習 reinforcement learning
    + TD, Q-learning, de-facektor(換臉)

##### 機器學習簡介

|分類       | 細分類                              | Features特徵                  | Label預測目標                          |
|----------|------------------------------------|-------------------------------|---------------------------------------|
| 監督式學習 | Binary Classification 二元分類      | 濕度、風向、風速、季節、氣壓...   | 只有0與1選項(Y/N) 0不下雨/1會下雨         |
| 監督式學習 | Multi-Class Classification 多元分類 | 濕度、風向、風速、季節、氣壓...   | 有多個選項(1sunny,2rainy,3windy,4snowy) |
| 監督式學習 | Regression 回歸分析                 | 濕度、風向、風速、季節、氣壓...   | 值是數值(計算題)<br>溫度可能是-50~50度範圍 |
| 非監督學習 | Clustering 群集                     | 濕度、風向、風速、季節、氣壓...  | 無label Cluster集群分析；目的是將資料依照特徵，分成幾<br>個相異性最大的群組，而群<br>組內的相似程度最高                                    |
| 強化學習   | Q-learning、TD(Temporal Difference)|                              | 強化學習的原理，藉由定義：動作<br>(Actions)、狀態(States)、<br>獎勵(Rewards)的方式，不<br>斷訓練機器循序漸進，學會<br>執行某項任務的演算法，常用於<br>動態系統及機器人控制等。                                   |


##### 認識鳶尾花資料集 Iris Datasets

###### 辨識 iris 分類 - 步驟3 「探索式資料分析」Exploratory Data Analysis

```python
# -*- coding: utf-8 -*-
"""
A_ch01_001_iris.py 2021.11.19

"""
import pandas as pd
import numpy as np

#讀取 'iris.csv' 成為 DataFrame irisdf
irisdf=pd.read_csv('iris.csv')

#檢視 DataFrame irisdf
irisdf.head(5)

irisdf.values.shape #(150,5)
irisdf.columns #檢視欄位

#============================================
#step1: 資料預處理及準備訓練資料 
#step1.1 裁取特徵屬性
X=irisdf.loc[:,'sepal_length':'petal_width']

#spep1.2 轉換target欄位
irisdf.species.value_counts() #檢視現有值
type(irisdf.species.values[0]) #str
#建立轉換字典 'class_mapping'
class_mapping={"setosa":0,"versicolor":1,"virginica":2}
#以字典 'class_mapping' 做轉換
irisdf['class']=irisdf['species'].map(class_mapping)
#target(labels) y
y=irisdf['class']

#檢視前5個 festures(X.values) (尺寸資料)
X.values[:5]
#features的shape  150個資料點,每點4個尺寸。 (150,4) 2維 array
type(X.values) #numpy.ndarray
X.values.shape #(150,4)
X.values.ndim #2 維

#檢視前5個 labels(y) (iris : 0,1,2)
y.values[:5]
#檢視 target datatype
type(y.values) #numpy.ndarray
y.values.shape #(150,)
y.values.ndim #1維
#==============================================================
#step 2. 以視覺化(圖形)做EDA
#%matplotlib
grr=pd.plotting.scatter_matrix(X,c=y.values, figsize=(15,15),marker='o',
                     hist_kwds={'bins':20},s=60,alpha=0.8)
#------------------------------------------------------
#step 2.1
#繪製箱型圖(box plot),檢視訓練資料的特徵值分布狀況(是否有離群值)
X.plot(kind='box') #iris_df.plot.box()

#---------------------------------------------------------
#繪製 "petal length (cm)" 對 "petal width (cm)" 的散佈圖
x1='petal_length'
y1='petal_width'
irisdf.plot.scatter(x1,y1,alpha=0.5)

#scattor
irisdf.plot.scatter('sepal_length','class',alpha=0.5)
#--------------------------------------------------------
#繪製 "sepal width" histogram 
irisdf["sepal_width"].plot.hist(bins=10,alpha=0.5)

#---------------------------------------------------------
#從DataFrame 'iris_df' 建立pair圖,顏色依y_train的值
grr=pd.plotting.scatter_matrix(X,c=y.values, figsize=(15,15),marker='o',
                     hist_kwds={'bins':20},s=60,alpha=0.8)

#===================================================================
#step 3. 訓練和測試資料, 使用scikit-larn train_test_split()
#        預設比例 0.75:0.25
from sklearn.model_selection import train_test_split

X_train,X_test,y_train,y_test=train_test_split(
X.values,y.values,random_state=0,test_size=0.25)

#檢視 train / test datasets
print('len(X_train)=',len(X_train),' len(X_test)=',len(X_test))

#檢視 data /target shape 
print('X_train.shape=',X_train.shape,' y_train.shape=',y_train.shape)
print('X_test.shape=',X_test.shape,' y_test.shape=',y_test.shape)

#檢視 train 前5個樣本
X_train[:5]
y_train[:5]

#==============================================================================
#step 4. 建立模型 --以決策樹(Decision Tree)
#匯入 DecisionTree Classifier
from sklearn.tree import DecisionTreeClassifier
#from sklearn.linear_model import LogisticRegression

#建立一個 DecisionTreeClassifier object 'tree01', random_state 亂數種子
tree01=DecisionTreeClassifier(random_state=0)

#訓練模型, 未作任何限制, 建了一棵完全發展樹,所有樹葉都是純的(pure)
treeModel01=tree01.fit(X_train,y_train)

#使用訓練資料集,驗證這個完全樹,treeModel01
#auc=1.0, 100%完全命中
treeModel01.score(X_train,y_train)

#但，改用測試資料集, auc=0.973
treeModel01.score(X_test,y_test)

#分析決策樹,以scikit-learn export_graphviz() 將樹視覺化, 存到 "tree02.dot" 檔
from sklearn.tree import export_graphviz
export_graphviz(treeModel01,out_file="tree01_0307.dot",class_names=["setosa","versicolor","virginica"],
               feature_names=list(X.keys()),impurity=False,filled=True)

#檢視 "tree02.dot" 檔
import graphviz

with open("tree01_0307.dot") as f:
    dot_graph=f.read()
graphviz.Source(dot_graph)

#===============================================================================
#step 5. 預測, 以X_test[0], 並以y_test[0] 對答案
#        使用訓練完成的模型, 對相同資料型態的特徵資料做預測
X_test[0] #array([5.8, 2.8, 5.1, 2.4])
X_test[0].shape #(4,) ,是一個一維的的資料, 與我們訓練模型時的訓練資料型態不同

#Reshape your data either using array.reshape(-1, 1) if your data has a single feature
#or array.reshape(1, -1) if it contains a single sample.
X_new=X_test[0].reshape(1,-1)
X_new.shape #檢視shape (1,4)
#將reshape後的資料點 X_new 丟給Model 'treeModel01'做預測
treeModel01.predict(X_new) #array([2]), 預測的結果是一個一維的ndarray 
#以 y_test[0] 對答案
y_test[0] #2

#===============================================================================
#step 5.1 預測,新採集的一朵鳶尾花樣本 尺寸為 (5,2.9,1,0.2)
#         以我們建立的模型預測判定此樣本品種:
#新採集的一朵鳶尾花樣本
sample_01=[5,2.9,1,0.2] 
#將list轉為shape:(1,4) 的 ndarray
new_array_01=np.array(sample_01).reshape(1,-1)
#以treeModel01做預測
prediction_01=treeModel01.predict(new_array_01)
#Reshape your data either using array.reshape(-1, 1) 
#if your data has a single feature or
#array.reshape(1, -1) if it contains a single sample.
prediction_01[0] #0

#以zip編一個鳶尾花品種名稱字典 "dict_iris"
dict_iris={no:'Iris-'+name for no,name 
           in zip(range(0,3),['Setosa','Versicolour','Virginica'])}
#印出預測鳶尾花樣本的品種
print("鳶尾花尺寸:")
print("花萼長:",sample_01[0],"cm |"," 花萼寬:",sample_01[1],"cm |",
     "花瓣長:",sample_01[2],"cm |"," 花瓣寬:",sample_01[2],"cm |","\n")
print("預測品種是:",dict_iris[prediction_01[0]])

#=====================================================================
#step 6. 評估模型準確率
#以test dataset X_test 給模型做預測,預測值存於 y_pred
y_pred=treeModel01.predict(X_test)

#檢視 y_pred (預測)
y_pred

#檢視 y_test (標準答案)
y_test

#自己寫程式計算accuracy (比較 預測值 'y_pred' 及 label 'y_test')
np.mean(y_pred==y_test) #以numpy.mean() 計算

#使用 model.score() 計算Model.score(X_test,y_test)
#使用 model.score() 計算
treeModel01.score(X_test,y_test)

#==============================================================
#step7.整合成一個生產系統 (極簡陽春版)
#A_ch01_009_iris_pred.py
#以joblib.dump() 將模型 knnModel dump 到現行目錄下,檔名 'iris_tree01.pkl'
import joblib #import joblib module
joblib.dump(treeModel01,'iris_tree01_0701.pkl')
```

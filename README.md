# SmartButler 项目实战--智能管家

**项目介绍**：    
集多个模块与一体的实战项目，为了对所学知识进行一个实践与应用。     

* **登录模块**：采用了`Bmob`后端云实现的用户系统（注册，登录以及用户信息的修改）。    
* **服务管家模块**：  接入了科大讯飞`TTS`引擎，实现与机器人的智能对话。使用`ListView`来显示对话列表，在自定义的`Adapter`中使用`getItemViewType`方法来区分是接收的消息还是发送的消息。同时在`getView`方法中对`convertView`进行了优化和复用（判断`convertView`是否为空复用`convertView`，通过`convertView.set/getTag`方法复用`ViewHolder`）。    
* **微信精选模块**：使用`okhttp`框架通过聚合数据的“微信精选”接口来获取新闻数据并通过`ListView`显示基础信息，点开后是通过`WebView`显示的原文信息。同样`ListView`采用的是自定义的`adapter`且对其进行了优化处理。  
* **美女社区模块**：使用`okhttp`框架通过第三方接口获取到图片信息并通过`GridView`显示出来，点击图片后通过`PhotoView`来展示并实现放大缩小功能。  
* **个人中心模块**：实现个人信息的修改。  
* **其他**：主要是通过第三方的接口实现一些扩展功能，如快递信息查询、自身位置定位、`zxing`二维码生成等等。  

封装了一些[util](/app/src/main/java/com/android/smartbutler/util)工具包 。  

**项目时间**：    
2018年7月 ~ 2018.8月      

**项目演示**：  
<img src="gif/SmartButler.gif" alt="Sample"  width="300" height="480">    



**所使用的到的控件/技术**：    


| 控件/技术 | 用途 |
|:---:|:---:|
|`ViewPager`|实现每个模块之间的跳转|
|`ListView`|各种列表的显示，采用了自定义Adapter，并在`getView`方法中对`ListView`进行了优化|
|`GridView`|采用网格布局显示图片|
|`SharedPreferences`|勾选了记住密码后保存用户信息|
|`Dialog`|自定义`Dialog`提示框|
|`WebView`|实现新闻详细信息的加载与显示|

**所使用的到的框架**：   

| 框架| 用途|
|:---:|:---:|
|[Litepal](https://github.com/LitePalFramework/LitePal)|存取全国各城市/地区数据，避免每次都从网络上获取|
|[okhttp](https://github.com/square/okhttp)|发起网络请求|
|[PhotoView](https://github.com/chrisbanes/PhotoView)|实现图片的展示以及放大缩小功能|
|[picasso](https://github.com/square/picasso)|加载图片|
|[gson](https://github.com/google/gson)|解析网络请求获取的json数据|

**所使用的第三方SDK**：   

| 框架| 用途|
|:---:|:---:|
|百度地图|实现自己的定位以及实时地图的显示|
|Bmob后端云|实现用户系统（注册、登录、修改）|

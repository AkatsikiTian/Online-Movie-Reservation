# Online-Movie-Reservation
This is a online platform for booking tickets. You can sign in and choose your favourite movie to watch.
在火狐浏览器的地址栏里输入http://localhost:8080/，即可进入晓天电影订票系统的用户端首页，如图1所示。进入时是未登录状态，用户以游客的方式访问。进入后端需要在地址栏中输入http://localhost:8080/login，输入正确的用户名和密码即可登录晓天电影订票系统的后台页面，如图2所示。后台登录成功后见图3。
 
图1 首页
 ![image](https://user-images.githubusercontent.com/99302543/189249387-6cd8e3f7-350d-400a-8618-84c01b0b84ef.png)
图2 晓天电影订票系统后台登录
 ![image](https://user-images.githubusercontent.com/99302543/189249433-281b4d57-79f3-4c50-a44c-65d8202e836e.png)

图3 后台主页
![image](https://user-images.githubusercontent.com/99302543/189249450-4638911c-a3a6-4629-b5eb-a30f848a3863.png)
4.	具体操作
4.1.	电影查看模块
4.1.1 首页
	用户在地址栏里输入http://localhost:8080/进入首页，如图4所示，在首页中用户可以以游客的方式浏览最热门的8部影片如图5和最新的8部影片如图6所示。用户可以首页的最上方看到最新资讯的轮播图，通过 和 来切换轮播图。用户可以在最上方的标题栏里的搜索框里搜索电影，搜索是动态的，后台上映的电影会根据用户输入的内容动态出现，如图7所示，用户点击下拉框中的提示可以跳转至电影详情页。用户可以点击页面右下角的 回到页面顶部。用户点击电影图片或电影名称会跳转至电影详情页面。
 ![image](https://user-images.githubusercontent.com/99302543/189249493-1741e244-81a5-479e-a36a-872b58b7d486.png)

图4
 ![image](https://user-images.githubusercontent.com/99302543/189249503-3c4d6945-3b1c-4e06-8d17-58fc67baebd4.png)

图5 热门影片
 ![image](https://user-images.githubusercontent.com/99302543/189249512-2aaf20b1-407d-406f-b9b6-3e4fed5274ec.png)

图6 最新上线
 ![image](https://user-images.githubusercontent.com/99302543/189249522-b2868773-c63d-4889-b3e4-e654ca5da5fd.png)

图7 搜索
4.1.2 电影分类

	用户在首页的标题栏里点击电影分类即可跳转至电影分类界面，如图8所示， 用户可以点击标题“选电影”下方的蓝色按钮进行电影类别的选择，如图8中有“最新”、“最热”、“喜剧”、“经典老片”、“科幻片”、剧情片、“悬疑片”和“测试”这8个分类。用户可以根据自己的喜好选择相应的分类进行跳转，如图9所示，每一页会显示8部电影，跳转页面后可以利用 进行不同分页的跳转。点击电影图片或电影名称会进入电影详情页面
 ![image](https://user-images.githubusercontent.com/99302543/189249535-aea24345-e167-4069-bb4b-e119a83bf6c8.png)

图8 电影分类
 ![image](https://user-images.githubusercontent.com/99302543/189249549-9b1a46d4-1159-4796-a597-871772ba7b52.png)

图9 电影分类跳转
4.1.3 电影详情

	用户点击图片或电影名称后会跳转至电影详情页，如图10所示。在该页面，用户可以得到电影的名称，点击量，电影类型，电影导演，电影主演，电影播放语言，电影时长，电影上映时间和简介等信息。在有下方有绿色字体标注的电影场数和选座购票按钮，左下方有返回上级按钮。
 ![image](https://user-images.githubusercontent.com/99302543/189249560-79e5b87c-35e9-4aae-9c88-bacd208d9807.png)

图10 电影详情

4.2.	用户订票模块
4.2.1 登录
用户点击标题栏登录或在未登录状态下点击 后会跳转至登录页面，如图11所示。在该页面，用户可以通过输入用户名和密码进行登录操作。用户可以通过左上角的返回返回上一个页面，从选座购票的页面跳转的情况下会提示“未登录，请先登录”。如果用户尚未注册账号可以点击“sign in”按钮下的“还没有账号？去注册”链接，跳转至注册界面。当然用户也可以从标题栏点击注册按钮进行跳转。登录成功后，标题栏右侧显示用户名和头像，如图12所示。
 ![image](https://user-images.githubusercontent.com/99302543/189249569-589d0ce6-d671-42dc-8f38-5fae4823a84c.png)

图11 登录页面
 ![image](https://user-images.githubusercontent.com/99302543/189249576-4279b905-867d-4e64-bd3a-750faca241ae.png)

图12 登录成功
4.2.2 注册
用户点击标题栏注册或在登录页面下点击“还没有账号？去注册”链接后会跳转至注册页面，如图13所示。在该页面，用户可以通过输入用户名、昵称、手机号和密码进行注册操作。用户需要输入不少于5个字符的用户名，不少于5位的密码，同时，在注册前要勾选“我已同意‘晓天电影订票系统’用户注册协议！”才能完成注册。注册成功后会跳转到登录页面。
 
 ![image](https://user-images.githubusercontent.com/99302543/189249587-27342473-82b0-4341-9b33-831edbf896a2.png)

图13 注册页面
4.2.3 选场次选座订票
用户登录成功后，如果选择的电影的场次不为0则可以点击 进行场次选择，如图14所示。场次中会以弹窗形式出现，用户可以在弹窗中看到场次信息包括影厅，影厅的大小，上映的时间，票价，并进行选择。用户选择好场次后会跳转选择座位界面，如图15。用户点击座椅，可以将座椅变成绿色并在左侧已选座位位置显示选择的座位号，如果该座位已经被别人购买了会显示红色，未购买显示灰色，如图16所示。用户在该界面可以输入手机号，默认手机号是注册时填写的，用户也可以更改订票的手机号，在选完座位并填写好手机号后可以点击“去付款”按钮进行订单生成。订单生成后跳转至订单生成页面如图17，在该页面用户可以详细阅读自己的购买信息，确认无误后，点击“确认付款”按钮进行付款，付款成功后页面将会跳转至首页，并提示订单下单成功，如图18。如果用户放弃该订单，可以点击“取消付款”按钮，订单将会被消除并跳转至首页。
 ![image](https://user-images.githubusercontent.com/99302543/189249593-6afa15e7-e4f8-4c68-ab1e-9d713205cdb8.png)

图14 选择场次
 ![image](https://user-images.githubusercontent.com/99302543/189249602-3a25875e-75f1-457d-9017-3e106042818a.png)

图15 选择座位
 ![image](https://user-images.githubusercontent.com/99302543/189249610-d653190d-8676-4094-a15c-7ef9844a03e3.png)

图16 选择座位
 ![image](https://user-images.githubusercontent.com/99302543/189249620-10668682-682d-4f76-b545-560e9e8b4117.png)

图17 订单生成
 ![image](https://user-images.githubusercontent.com/99302543/189249624-57ca90e9-f890-4a46-a65e-f9b78309ec4b.png)
图18 订单付款成功界面

4.3.	用户个人信息与订单模块
4.3.1 用户个人信息
用户登录成功后，点击头像右侧的 ，可以显示下拉菜单，如图19所示。用户点击“个人信息”，将会跳转个人信息界面，如图20所示。用户点击个人信息简介右侧的操作可以对个人信息进行修改，如图21所示，用户可以编辑自己的头像，用户名，昵称，性别和手机号。修改成功后点击“确定”按钮保存修改，点击“取消”按钮取消修改。
 
图19 下拉菜单
 ![image](https://user-images.githubusercontent.com/99302543/189249682-ac67587a-e7ec-4d74-a3b4-ac4a4b237a25.png)

图20 个人信息界面
 
图21 修改个人信息
4.3.2 修改密码
用户登录成功后，点击头像右侧的 ，可以显示下拉菜单。用户点击“修改密码”，将会跳转修改密码界面，用户点击个人信息页面的 同样可以跳转至修改密码页面，如图22所示。用户可以输入旧密码和新密码进行密码修改，密码修改成功后会退出登录并跳转至登录页面。
 
图22 修改密码
4.3.3 用户订单
用户登录成功后，点击标题栏中的 菜单跳转至个人订单页面，也可以从个人中心中点击我的订单跳转至订单页面。用户可以在该页面选择退款或查看历史订单，如图23所示。如果点击退款按钮，订单则会显示等待管理员审核，如果管理员审核通过则显示已退款，如果审核不通过则显示管理员拒绝退款。 
 ![image](https://user-images.githubusercontent.com/99302543/189249699-d3980629-1b65-42b1-bf14-7db473291607.png)

图23 我的订单页面
4.3.4 退出登录
用户登录成功后，点击头像右侧的 ，可以显示下拉菜单，用户点击“退出登录”，将会跳转至登录页面，如图24所示。用户本地存储的token和个人信息就被删除。
 
图24 退出登录
4.4.	后台管理系统
4.4.1 后台登录
用户在地址栏中输入localhost:8080/login进入后台登录界面。用户需要输入后台的用户名和密码进行登录，如图25所示。
 
图25 后台登录
4.4.2 后台首页
用户登录成功后进入首页。在首页中，用户可以看到当前录入的电影总数，注册的用户人数，所有的订单总额，目前放映的电影部数。同时用柱状图和比例图分别显示最热门的5部影片和最近5天的订单数量，如图26所示。用户可以点击左侧侧边栏选择操作页面。
 ![image](https://user-images.githubusercontent.com/99302543/189249716-affc0e3f-68d9-43b8-8a3a-1f5cf31f4cf1.png)

图26 后台管理系统首页
4.4.3 员工管理
用户登录成功后进入首页。用户点击侧边栏的员工管理进入员工管理页面，如图27所示。用户在该界面可以对员工用户进行新增、修改、删除和查询。用户点击编辑按钮可以进行该条员工信息的修改，如图28所示。用户可以修改员工的用户名、姓名、身份证号和手机号，还可以设定该用户的封禁状态。用户点击新增按钮可以新增用户，如图29所示。用户可以点击删除按钮可以删除用户，同时可以勾选表格前面 ，进行批量删除，在选择多条数据后点击批量删除按钮，可以进行批量删除。
 ![image](https://user-images.githubusercontent.com/99302543/189249727-fbec3a2e-57b0-47d9-b8e9-ffde87fcef83.png)

图27 员工管理
 
图28 修改员工信息
 
图29 新增用户信息
4.4.4 分类管理
用户登录成功后进入首页。用户点击侧边栏的分类管理进入分类管理页面，如图30所示。用户在该界面可以对电影分类进行新增、修改和删除。用户点击编辑按钮可以进行该条分类信息的修改，如图31所示。用户可以修改分类名称。用户点击新增按钮可以新增分类，如图32所示。用户可以点击删除按钮可以删除分类。
 
图30 分类管理
 
图31 分类修改
 
图32 新增分类
4.4.5 电影管理
用户登录成功后进入首页。用户点击侧边栏的电影管理进入电影管理页面，如图33所示。用户在该界面可以对电影进行新增、修改、删除和查询。用户点击编辑按钮可以进行该条电影信息的修改，如图34所示。用户可以修改电影的名称、缩略图、电影时长、电影分类、上映时间、导演、主演、语言、电影描述和销售状态。用户点击新增按钮可以新增电影，如图35所示。用户可以点击删除按钮可以删除电影，同时可以勾选表格前面 ，进行批量删除，在选择多条数据后点击批量删除按钮，可以进行批量删除。
 ![image](https://user-images.githubusercontent.com/99302543/189249748-856f6c34-0537-4c60-bfd4-9bbc10b28ddb.png)

图33 电影管理
 ![image](https://user-images.githubusercontent.com/99302543/189249756-3d7caf27-05f1-46b3-aff1-30a90d351e75.png)

图34 电影信息修改
 
图35 新增电影
4.4.6 热门资讯
用户登录成功后进入首页。用户点击侧边栏的热门资讯进入热门资讯管理页面，如图36所示。用户在该界面可以对热门资讯进行新增、修改、删除和查询。热门资讯以轮播图的形式显示在用户端首页。用户点击编辑按钮可以进行该条资讯信息的修改，如图37所示。用户可以修改热门资讯的海报名称和海报缩略图。用户点击新增按钮可以新增热门资讯，如图38所示。用户可以点击删除按钮可以删除热门资讯，同时可以勾选表格前面 ，进行批量删除，在选择多条数据后点击批量删除按钮，可以进行批量删除。
 
图36 热门资讯
 
图37 热门资讯编辑
 
图38 热门资讯新增
4.4.7 用户管理
用户登录成功后进入首页。用户点击侧边栏的用户管理进入用户管理页面，如图39所示。用户在该界面可以对用户进行新增、修改、删除和查询。用户点击“封禁”或“解封”按钮可以进行该条用户信息的状态的修改，如图40所示。用户被封禁后无法登录用户端，在后台显示的状态也会变为封禁状态。用户点击新增按钮可以新增用户，所有后台新增的用户初始密码都为“123456”，如图41所示。用户可以点击删除按钮可以删除用户，同时可以勾选表格前面 ，进行批量删除，在选择多条数据后点击批量删除按钮，可以进行批量删除。
 
图39 用户管理
 
图40 封禁/解封用户
 
图41 新增用户
4.4.8 影厅管理
用户登录成功后进入首页。用户点击侧边栏的影厅管理进入影厅管理页面，如图42所示。用户在该界面可以对影厅进行新增、修改和删除。用户点击编辑按钮可以进行该条影厅信息的修改，如图43所示。用户可以修改影厅的名称和影厅的座椅个数。用户点击新增按钮可以新增影厅，如图44所示。用户可以点击删除按钮可以删除不需要的影厅。
 
图42 影厅管理
 
图43 修改影厅
 
图44 新增影厅
4.4.9 影厅管理
用户登录成功后进入首页。用户点击侧边栏的放映管理进入放映管理页面，如图45所示。用户在该界面可以对放映的电影进行新增、修改和删除。用户点击编辑按钮可以进行该条放映信息的修改，如图46所示。用户可以修改放映的电影、影厅、票价和放映日期。用户点击新增按钮可以新增放映场次，如图47所示。用户可以点击删除按钮可以删除不需要的放映场次。
 
图45 放映管理
 
图46 放映修改
 
图47 新增放映
4.4.10 订单管理
用户登录成功后进入首页。用户点击侧边栏的订单管理进入订单管理页面，如图48所示。用户在该界面可以对订单进行查看和退款修改。管理员在前台用户退款的订单处会有“同意”和“拒绝”两个按钮来对退款进行操作。管理员可以拒绝退款，如图49所示，点击 按钮则拒绝退款。管理员也可以点击同意按钮，如图50所示，点击 按钮同意退款。
 ![image](https://user-images.githubusercontent.com/99302543/189249800-8a0895cd-522e-42c9-bacc-27a45d3a6e2f.png)

图48 订单管理
 
图49 拒绝退款
 
图50 同意退款
4.4.11 修改密码
用户登录成功后，点击用户名称右侧的 ，可以显示下拉菜单。用户点击“修改密码”，如图51所示，将会跳转修改密码界面，如图52所示。用户可以输入旧密码和新密码进行密码修改，密码修改成功后会退出登录并跳转至登录页面。
 
图51 修改密码
 
图52 修改密码页面

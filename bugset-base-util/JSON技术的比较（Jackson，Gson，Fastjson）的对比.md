1.json-lib
json-lib最开始的也是应用最广泛的json解析工具，json-lib 不好的地方确实是依赖于很多第三方包，包括commons-beanutils.jar，commons-collections-3.2.jar，commons-lang-2.6.jar，commons-logging-1.1.1.jar，ezmorph-1.0.6.jar，
对于复杂类型的转换，json-lib对于json转换成bean还有缺陷，比如一个类里面会出现另一个类的list或者map集合，json-lib从json到bean的转换就会出现问题。
json-lib在功能和性能上面都不能满足现在互联网化的需求。

2.开源的Jackson
相比json-lib框架，Jackson所依赖的jar包较少，简单易用并且性能也要相对高些。而且Jackson社区相对比较活跃，更新速度也比较快。
Jackson对于复杂类型的json转换bean会出现问题，一些集合Map，List的转换出现问题。Jackson对于复杂类型的bean转换Json，转换的json格式不是标准的Json格式

3.Google的Gson
Gson是目前功能最全的Json解析神器，Gson当初是为因应Google公司内部需求而由Google自行研发而来，但自从在2008年五月公开发布第一版后已被许多公司或用户应用。
Gson的应用主要为toJson与fromJson两个转换函数，无依赖，不需要例外额外的jar，能够直接跑在JDK上。而在使用这种对象转换之前需先创建好对象的类型以及其成员才能成功的将JSON字符串成功转换成相对应的对象。类里面只要有get和set方法，Gson完全可以将复杂类型的json到bean或bean到json的转换，是JSON解析的神器。
Gson在功能上面无可挑剔，但是性能上面比FastJson有所差距。

4.阿里巴巴的FastJson
Fastjson是一个Java语言编写的高性能的JSON处理器,由阿里巴巴公司开发。无依赖，不需要例外额外的jar，能够直接跑在JDK上。
FastJson在复杂类型的Bean转换Json上会出现一些问题，可能会出现引用的类型，导致Json转换出错，需要制定引用。
FastJson采用独创的算法，将parse的速度提升到极致，超过所有json库。

总结
在项目选型的时候可以使用Google的Gson和阿里巴巴的FastJson两种并行使用
1.如果只是功能要求，没有性能要求，可以使用google的Gson，
2.如果有性能上面的要求可以使用Gson将bean转换json确保数据的正确，使用FastJson将Json转换Bean

二、Google的Gson包的使用简介
Gson类：解析json的最基础的工具类
JsonParser类：解析器来解析JSON到JsonElements的解析树
JsonElement类：一个类代表的JSON元素
JsonObject类：JSON对象类型
JsonArray类：JsonObject数组
TypeToken类：用于创建type，比如泛型List<?>

bean转换json
    Gson gson = new Gson();
    String json = gson.toJson(obj);

json转换bean
    Gson gson = new Gson();
    String json = "{\"id\":\"2\",\"name\":\"Json技术\"}";
    Book book = gson.fromJson(json, Book.class);

json转换复杂的bean，比如List，Set
将json转换成复杂类型的bean,需要使用TypeToken
    Gson gson = new Gson();
    String json = "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";
    //将json转换成List
    List list = gson.fromJson(json,new TypeToken<LIST>() {}.getType());
    //将json转换成Set
    Set set = gson.fromJson(json,new TypeToken<SET>() {}.getType());

通过json对象直接操作json以及一些json的工具
a)格式化Json
String json = "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";
Gson gson = new GsonBuilder().setPrettyPrinting().create();
JsonParser jp = new JsonParser();
JsonElement je = jp.parse(json);
json = gson.toJson(je);

b)判断字符串是否是json,通过捕捉的异常来判断是否是json
String json = "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";
boolean jsonFlag;
try {
	new JsonParser().parse(str).getAsJsonObject();
	jsonFlag = true;
} catch (Exception e) {
	jsonFlag = false;
}

从json串中获取属性
 String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
  String propertyName = 'id';
  String propertyValue = "";
  try {
    JsonParser jsonParser = new JsonParser();
    JsonElement element = jsonParser.parse(json);
    JsonObject jsonObj = element.getAsJsonObject();
    propertyValue = jsonObj.get(propertyName).toString();
  } catch (Exception e) {
  	propertyValue = null;
  }

  除去json中的某个属性
  String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
String propertyName = 'id';
JsonParser jsonParser = new JsonParser();
JsonElement element = jsonParser.parse(json);
JsonObject jsonObj = element.getAsJsonObject();
jsonObj.remove(propertyName);
json = jsonObj.toString();

向json中添加属性
String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
String propertyName = 'desc';
Object propertyValue = "json各种技术的调研";
JsonParser jsonParser = new JsonParser();
JsonElement element = jsonParser.parse(json);
JsonObject jsonObj = element.getAsJsonObject();
jsonObj.addProperty(propertyName, new Gson().toJson(propertyValue));
json = jsonObj.toString();

修改json中的属性
String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
String propertyName = 'name';
Object propertyValue = "json各种技术的调研";
JsonParser jsonParser = new JsonParser();
JsonElement element = jsonParser.parse(json);
JsonObject jsonObj = element.getAsJsonObject();
jsonObj.remove(propertyName);
jsonObj.addProperty(propertyName, new Gson().toJson(propertyValue));
json = jsonObj.toString();

判断json中是否有属性
String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
String propertyName = 'name';
boolean isContains = false ;
JsonParser jsonParser = new JsonParser();
JsonElement element = jsonParser.parse(json);
JsonObject jsonObj = element.getAsJsonObject();
isContains = jsonObj.has(propertyName);

json中日期格式的处理
GsonBuilder builder = new GsonBuilder();
builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
Gson gson = builder.create();

然后使用gson对象进行json的处理，如果出现日期Date类的对象，就会按照设置的格式进行处理
i)json中对于Html的转义
Gson gson = new Gson();
这种对象默认对Html进行转义，如果不想转义使用下面的方法
 GsonBuilder builder = new GsonBuilder();
    builder.disableHtmlEscaping();
    Gson gson = builder.create();


### fastjson

bean转换json
将对象转换成格式化的json
    JSON.toJSONString(obj,true);

将对象转换成非格式化的json
    JSON.toJSONString(obj,false);

obj设计对象
对于复杂类型的转换,对于重复的引用在转成json串后在json串中出现引用的字符,比如 ref&quot;:&quot;ref":"[0].books[1]
Student stu = new Student();
Set books= new HashSet();
Book book = new Book();
books.add(book);
stu.setBooks(books);
List list = new ArrayList();
for(int i=0;i<5;i++)
list.add(stu);
String json = JSON.toJSONString(list,true);

json转换bean
String json = "{\"id\":\"2\",\"name\":\"Json技术\"}";
Book book = JSON.parseObject(json, Book.class);

)json转换复杂的bean，比如List，Map
String json = "[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\",\"name\":\"java技术\"}]";
//将json转换成List
List list = JSON.parseObject(json,new TypeReference<ARRAYLIST>(){});
//将json转换成Set
Set set = JSON.parseObject(json,new TypeReference<HASHSET>(){});

通过json对象直接操作json
a)从json串中获取属性
String propertyName = 'id';
String propertyValue = "";
String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
JSONObject obj = JSON.parseObject(json);
propertyValue = obj.get(propertyName));

b)除去json中的某个属性
String propertyName = 'id';
String propertyValue = "";
String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
JSONObject obj = JSON.parseObject(json);
Set set = obj.keySet();
propertyValue = set.remove(propertyName);
json = obj.toString();

c)向json中添加属性
String propertyName = 'desc';
Object propertyValue = "json的玩意儿";
String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
JSONObject obj = JSON.parseObject(json);
obj.put(propertyName, JSON.toJSONString(propertyValue));
json = obj.toString();

d)修改json中的属性
String propertyName = 'name';
Object propertyValue = "json的玩意儿";
String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
JSONObject obj = JSON.parseObject(json);
Set set = obj.keySet();
if(set.contains(propertyName))
obj.put(propertyName, JSON.toJSONString(propertyValue));
json = obj.toString();

判断json中是否有属性
String propertyName = 'name';
boolean isContain = false;
String json = "{\"id\":\"1\",\"name\":\"Json技术\"}";
JSONObject obj = JSON.parseObject(json);
Set set = obj.keySet();
isContain = set.contains(propertyName);

json中日期格式的处理
Object obj = new Date();
String json = JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss.SSS");
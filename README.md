
#DBHelper
#面向对象的SQLite框架

---

#To Use

## 1.In Gradle

```gradle

compile 'me.ccj.dbhelper:dbhelper:1.0.0'

```
## 2.In Maven 

```maven

<dependency>
  <groupId>me.ccj.dbhelper</groupId>
  <artifactId>dbhelper</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>

```


---



#Geting Started

## 1.建立bean文件,配置注解
```java
/**
 * Created by ccj on 2017/1/10.
 */

/**
 * Bean文件 用注解#{@DBTable},绑定表名,
 * 用#{@DBPrimaryKey},来设置自增长,主外键关系.
 * 其他,用属性名作为表属性
 */
@DBTable("tb_user")
public class User {

    @DBPrimaryKey
    private Long id;

    public String name;

    public String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public User() {
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
/**
 create table if not exists tb_user (  id integer PRIMARY KEY autoincrement, name text, password text );
*/

```
## 1.工厂调用

```
public class MainActivity extends AppCompatActivity {
    int index = 0;
    BaseDao cityDao;
    BaseDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //要保证初始化一次,避免重复新建对象    
        userDao = BaseDaoFactory.getInstance().getDBDao(User.class);
        cityDao = BaseDaoFactory.getInstance().getDBDao(City.class);

    }

    /**
     * 保存城市
     * @param v
     */
    public void saveCity(View v) {
        City city = new City("青岛" + (index++), "0200");
        cityDao.insert(city);


    }

    /**
     * 保存用户
     * @param v
     */
    public void saveUser(View v) {
        index++;
        User user = new User("ccj" + index, "123456");
        TLog.error(user.toString());
        userDao.insert(user);

    }

    /**
     * 更新用户
     * @param v
     */
    public void updateUser(View v) {
        User old = new User();
        old.name = "ccj1";
        User news = new User("ccj1", "123");
        userDao.update(old, news);

    }

    /**
     * 查询用户
     * @param v
     */
    public void getUser(View v) {
        User user = new User();
        List<Object> userList = userDao.query(user, null, null);
        for (Object user1 : userList) {
            TLog.error(user1.toString());
        }
    }

    /**
     * 删除用户
     * @param v
     */
    public void deleteUser(View v) {
        User user = new User();
        userDao.delete(user);

    }

}

```






---
#Thinking

1.**注解**`DBPrimaryKey`得到主外键关系,`DBTable`得到表名.

2.根据 上述两个注解修饰的`Bean`文件**建表**,没有注解修饰的属性,以小写的形式生成字段.

3.顺便在`步骤2`中,将`Bean`中`Field`的集合`fieldArray`,缓存到类`DBDao`中,方便后续调用.

3.在单例工厂`DBDaoFactory`, **反射**,实例化`DBDao`文件.

4.增删改查,根据对象拿到对象的Field,需要Field的值,就用` field.set(object, value)`拿到属性的值.作为搜索,或者添加条件.

5.查询,直接将数据通过`fieldArray`,对反射得到的对象,进行赋值.



#优点:
一套机制,完成所有对象的操作.

#缺点:
功能有限,后续将会扩展


#liscense license




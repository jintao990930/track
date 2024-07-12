# MyBatis-Plus-Query

## 简介
MyBatis-Plus-Query是一个用于构建MyBatis-Plus查询条件的辅助工具，旨在简化查询条件的编写过程。

## 特性
- 注解式声明，灵活且易用的查询条件构建
- 可扩展的条件构建过程
- 可开关的数据缓存配置

## 版本
- JDK8 或更高版本
- MyBatis-Plus 3.5.0 或更高版本

## 使用
1. 添加依赖
<p>首先，<strong>确保您的项目中已经添加了MyBatis-Plus依赖</strong>，其次将MyBatis-Plus-Query添加到您的项目中。</p>

```xml
<!-- mybatis-plus-query -->
<dependency>
    <groupId>site.lianwu</groupId>
    <artifactId>mybatis-plus-query</artifactId>
    <version>1.0.1</version>
</dependency>
```

2. 使用注解
<p>@Query用于声明一个查询条件，可重复作用在字段上，而@NestedQuery用于表明一个待嵌套解析的字段。</p>

```java
@Data
public class UserDto {


    @Query(validation = Validator.NotBlank.class, column = "email", value = ConditionType.Like.class, logic = Logic.OR, groupId = "KEYWORD")
    @Query(validation = Validator.NotBlank.class, column = "name", value = ConditionType.Like.class, logic = Logic.OR, groupId = "KEYWORD")
    private String keyword;

    @NestedQuery
    private UserDto2 userDto2;

}

@Data
public class UserDto2 {

    @Query(ConditionType.Le.class)
    private Integer age;

}

```
3. 构建并执行查询
```java
    UserDto userDto = new UserDto();
    userDto.setKeyword("test");
    UserDto2 userDto2 = new UserDto2();
    userDto2.setAge(35);
    userDto.setUserDto2(userDto2);
    
    QueryWrapper<User> wrapper = QueryWrappers.parse(userDto);
    // WHERE (age <= 35 AND (email LIKE '%test%' OR name LIKE '%test%'))
    userMapper.selectList(wrapper);
```

## 原理
<ol>
<li>注解驱动：MyBatis-Plus-Query利用Java的注解（Annotation）机制来声明查询条件。开发者通过在类字段上使用@Query和@NestedQuery注解来定义查询逻辑。</li>
<li>条件解析：在构建查询时，MyBatis-Plus-Query会遍历条件对象的所有字段，提取并解析带有@Query和@NestedQuery注解的字段，最终生成MP的QueryWrapper对象。
<ul>
<li>对于@Query中的属性值：
<ul>
<li>value：查询条件的比较方式，默认Eq（=）</li>
<li>column：指定条件列名，默认将字段名驼峰转下划线</li>
<li>logic：逻辑操作符，默认（and），表示分组内条件间的逻辑关系</li>
<li>groupId：分组ID（分组是对SQL中括号的抽象），默认根组，即查询条件最外层括号 WHERE (...)</li>
<li>validation：校验规则，默认字段值不为空，校验通过的字段值才参与条件构建</li>
</ul>
</li>
<li>对于@NestedQuery中的属性值：
<ul>
<li>value：是否包含继承字段，默认不包含</li>
</ul>
</li>
</ul>
<p>以默认的条件包装处理器为例，解析过程中优先处理根分组，其次处理其余分组，分组间约定使用and连接。</p>
<p>由于底层使用了无序的数据结构，意味着对其余分组的处理是无序的，同样地，分组内的条件设置也是无序的。</p>
<p>因此，<strong>若使用or逻辑条件处理，为确保查询结果的正确性，建议为这些or查询条件声明一个单独的分组。</strong></p>
</li>
</ol>

## 其他/扩展

- 数据缓存：MyBatis-Plus-Query提供了缓存数据的配置选项。在针对反射的性能优化上，默认开启对字段或者实例获取的缓存；在查询条件类的构建过程中，默认开启了对提取数据的缓存。
  
- 条件补充：解析过程中允许用户添加额外的查询条件，通过使用QueryOption的Builder补充不同分组的后置处理器以补充查询逻辑，在默认的处理器中，<strong>后置处理器只针对查询注解中声明的分组有效。这意味着即使补充了某个分组的后置处理逻辑，若该分组未在注解中声明，则相应的后置处理逻辑也不会被执行。</strong>
  
- 自定义校验规则/查询条件：开发者可自定义校验类（实现Validation接口）或自定义查询条件类（实现Condition接口），在@Query相应的属性值中指定类对象，以满足业务需求。
  
- 插件式处理器：开发者可以自定义处理器（实现QueryFieldProcessor、QueryClassProcessor或QueryWrapperProcessor接口），在外观工具类QueryWrappers中实现插件式的组装，以支持更复杂的查询需求。

## 鸣谢
Gary（zjn）、Link（lsb）、Lauv（lyh）

## License
[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

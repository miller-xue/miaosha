CREATE TABLE `user`(
  `id` BIGINT(20) NOT NULL COMMENT  '用户ID, 手机号',
  `nickname` VARCHAR(255) NOT NULL ,
  `password` VARCHAR(32) DEFAULT NULL  COMMENT 'MD5(MD5(pass明文+ 固定salt) + salt )',
  `salt` VARCHAR(10) DEFAULT NULL ,
  `head` VARCHAR(128) DEFAULT NULL COMMENT  '头像,云存储ID',
  `register_date` DATETIME DEFAULT NULL  COMMENT '注册时间',
  `last_login_date` DATETIME DEFAULT NULL COMMENT  '上次登陆时间',
  `login_count` INT(11) DEFAULT '0' COMMENT '登陆次数',
  PRIMARY KEY (`id`)
)ENGINE = innoDB DEFAULT CharSET = utf8mb4;

CREATE TABLE `goods` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT  '商品ID',
  `name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `img` varchar(64) DEFAULT NULL COMMENT '商品的图片',
  `detail` longtext  COMMENT '商品详情介绍',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `stock` int(11) DEFAULT '0' COMMENT '商品库存, -1表示没有限制',
  PRIMARY KEY (`id`)
)ENGINE = innoDB DEFAULT CharSET = utf8mb4;


CREATE TABLE `seckill_goods` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT  '商品ID',
  `goods_id` BIGINT(20) DEFAULT NULL COMMENT  '商品id',
  `seckill_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `stock_count` int(11) DEFAULT '0' COMMENT '商品库存, -1表示没有限制',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `ende_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
)ENGINE = innoDB DEFAULT CharSET = utf8mb4;


CREATE TABLE `order_info`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT  '订单id',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户id',
  `goods_id` BIGINT(20) DEFAULT NULL COMMENT  '商品id',
  `delivery_addr_id` BIGINT(20) DEFAULT NULL COMMENT  '收货地址Id',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余的商品名称',
  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `order_channel` TINYINT(4) DEFAULT '0' COMMENT '1 pc, 2android, 3ios',
  `status` TINYINT(4) DEFAULT '0' COMMENT '订单状态, 0 新建未支付, 1已支付, 2已发货, 3已收货,4已退款,5已完成',
  `create_date` datetime DEFAULT NULL COMMENT '订单创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
)ENGINE = innoDB DEFAULT CharSET = utf8mb4;



CREATE TABLE `seckill_order`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT  '秒杀订单id',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户id',
  `goods_id` BIGINT(20) DEFAULT NULL COMMENT  '商品id',
  `order_id` BIGINT(20) DEFAULT NULL COMMENT  '订单id',
  PRIMARY KEY (`id`)
)ENGINE = innoDB DEFAULT CharSET = utf8mb4;

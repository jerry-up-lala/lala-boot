CREATE DATABASE IF NOT EXISTS `lala_boot` default charset utf8mb4 COLLATE utf8mb4_general_ci;

DROP TABLE IF EXISTS `lala_boot`.`sys_gen_table`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`sys_gen_table` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `table_schema` varchar(255) DEFAULT NULL COMMENT '库名',
  `table_name` varchar(255) DEFAULT NULL COMMENT '表名',
  `package_name` varchar(255) DEFAULT NULL COMMENT '包名',
  `class_name` varchar(255) DEFAULT NULL COMMENT '类名',
  `function_name` varchar(255) DEFAULT NULL COMMENT '功能名',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT '' COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`),
  UNIQUE INDEX(`table_schema`, `table_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8mb4 COMMENT = '数据库表信息';

DROP TABLE IF EXISTS `lala_boot`.`sys_gen_column`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`sys_gen_column` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `table_id` bigint(20) DEFAULT NULL COMMENT '表ID(sys_gen_table.id)',
  `column_name` varchar(255) DEFAULT NULL COMMENT '列名',
  `field_name` varchar(255) DEFAULT NULL COMMENT '字段名',
  `field_type` varchar(255) DEFAULT NULL COMMENT '字段类型',
  `field_comment` varchar(255) DEFAULT NULL COMMENT '字段备注',
  `field_dict_key` varchar(255) DEFAULT NULL COMMENT '字典类型',
  `field_ordinal_position` int(11) NOT NULL COMMENT '字段排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT '' COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`),
  KEY `sys_gen_column` (`table_id`) USING HASH,
  UNIQUE INDEX(`table_id`, `column_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8mb4 COMMENT = '数据库字段配置';

DROP TABLE IF EXISTS `lala_boot`.`sys_log_request`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`sys_log_request` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(50) DEFAULT NULL COMMENT '集团ID',
  `login_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `user_id` varchar(255) NOT NULL COMMENT '账号ID',
  `user_info` varchar(255) NOT NULL COMMENT '用户信息',
  `api_name` varchar(50) DEFAULT NULL COMMENT '接口名称',
  `class_method` varchar(1024) COMMENT '类方法',
  `class_params` text COMMENT '请求参数',
  `response_success` bit(1) COMMENT '响应状态，true-成功，false-失败',
  `response_error_code` varchar(50) COMMENT '响应异常码',
  `response_error_msg` varchar(255) COMMENT '响应异常信息',
  `response_time` bigint(20) COMMENT '响应耗时(毫秒)',
  `response_time_format` varchar(255) COMMENT '响应耗时格式化',
  `request_time` datetime COMMENT '操作时间',
  `user_agent` varchar(255) NOT NULL COMMENT '请求平台',
  `client_ip` varchar(255) NOT NULL COMMENT '客户端IP',
  `scheme` varchar(255) NOT NULL COMMENT '请求协议',
  `servlet_path` varchar(255) NOT NULL COMMENT 'servlet 路径',
  `servlet_method` varchar(255) NOT NULL COMMENT '请求方式',
  `request_url` varchar(255) NOT NULL COMMENT '请求路径',
  `request_params` text NOT NULL COMMENT '请求参数',
  `request_body` text NOT NULL COMMENT '请求body',
  `server_name` varchar(255) NOT NULL COMMENT '服务名',
  `server_port` int(11) NOT NULL COMMENT '服务端口',
  `remote_host` varchar(255) NOT NULL COMMENT '客户端host',
  `remote_port` int(11) NOT NULL COMMENT '客户端端口',
  `local_addr` varchar(255) NOT NULL COMMENT '服务端地址',
  `local_name` varchar(255) NOT NULL COMMENT '服务端名称',
  `local_port` int(11) NOT NULL COMMENT '服务端端口',
  `request_url_info` varchar(255) NOT NULL COMMENT '请求信息',
  `client_info` varchar(255) NOT NULL COMMENT '客户端信息',
  `server_info` varchar(255) NOT NULL COMMENT '服务端信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT '' COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8mb4 COMMENT = '请求日志表';

DROP TABLE IF EXISTS `lala_boot`.`sys_user`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`sys_user` (
  `id` varchar(50) NOT NULL COMMENT '账号ID',
  `login_name` varchar(50) NOT NULL COMMENT '账号(全局唯一)',
  `pass_word` varchar(512) NOT NULL COMMENT '用户密码',
  `real_name` varchar(50) NOT NULL COMMENT '姓名',
  `state` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态[true-启用]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '账号表';

INSERT INTO `lala_boot`.`sys_user` (`id`, `login_name`, `pass_word`, `real_name`, `state`, `create_user`, `create_time`) VALUES 
('1698900184022351875', 'admin', '8076a8ca0144c8bcaf2b064961132ccb0b291c7b75622687330cbb53c3a33dbc6751fb6e6eb73a5e1dc0c16e5946f8cbddb705950b93edfe2afb99020b69ab10c0c10b769ae36a465a6fabfeab3da2c7ab88aa1b2d08e1c209d957fdc6cfec5bb67def365e6bf62ed374ef3cad4174f4f01b2b1e94391d7fd90aa7188defab26',
'系统管理员', b'1', '1698900184022351875', now());

DROP TABLE IF EXISTS `lala_boot`.`sys_tenant`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`sys_tenant` (
  `id` varchar(50) NOT NULL COMMENT '集团ID',
  `tenant_name` varchar(50) NOT NULL COMMENT '集团名称(全局唯一)',
  `state` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态[true-启用]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '租户表';

INSERT INTO `lala_boot`.`sys_tenant` (`id`, `tenant_name`, `state`, `create_user`, `create_time`) VALUES 
('1698900184022351876', '样例集团', b'1', '1698900184022351875', now());

DROP TABLE IF EXISTS `lala_boot`.`sys_menu`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父ID',
  `type` bigint(20) NOT NULL DEFAULT 1 COMMENT '类型(1-菜单/2-按钮)',
  `locale` varchar(255) NOT NULL DEFAULT '' COMMENT '名称(语言包键名)',
  `locale_zh_cn` varchar(255) NOT NULL DEFAULT '' COMMENT '名称(中文)',
  `requires_auth` bit(1) NOT NULL DEFAULT 0 COMMENT '是否需要登录鉴权',
  `access_codes` varchar(255) DEFAULT NULL COMMENT '访问code，多个用逗号分隔',
  `name` varchar(255) DEFAULT NULL COMMENT '组件名称',
  `path` varchar(255) DEFAULT NULL COMMENT '路由地址',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单配置icon',
  `hide_in_menu` bit(1) DEFAULT NULL COMMENT '是否在左侧菜单中隐藏该项',
  `hide_children_in_menu` bit(1) DEFAULT NULL COMMENT '强制在左侧菜单中显示单项',
  `no_affix` bit(1) DEFAULT NULL COMMENT '如果设置为true，标签将不会添加到tab-bar中',
  `menu_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序号(值越高，越靠后)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8mb4 COMMENT='菜单表';

INSERT INTO `lala_boot`.`sys_menu` (`id`, `parent_id`, `type`, `locale`, `locale_zh_cn`, `requires_auth`, `access_codes`, `name`, `path`, `icon`, `hide_in_menu`, `hide_children_in_menu`, `no_affix`, `menu_order`, 
`create_time`, `create_user`, `update_time`, `update_user`, `deleted`) VALUES 
(1, 0, 1, 'menu.dashboard', '仪表盘', b'0', '', 'Dashboard', '/dashboard', 'icon-dashboard', NULL, NULL, NULL, 1, now(), '1698900184022351875', NULL, NULL, b'0'),
(2, 1, 1, 'menu.workplace', '工作台', b'0', '', 'Workplace', 'workplace', NULL, b'0', b'0', b'0', 2, now(), '1698900184022351875', NULL, NULL, b'0'),
(3, 0, 1, 'menu.crud', 'crud样例', b'1', 'CRUD', 'Crud', '/crud', 'icon-select-all', NULL, NULL, NULL, 3, now(), '1698900184022351875', NULL, NULL, b'0'),
(4, 3, 1, 'menu.crud.arco', '原生模式', b'1', 'CRUD:ARCO', 'CrudArco', 'crud-arco', NULL, b'0', b'0', b'0', 4, now(), '1698900184022351875', NULL, NULL, b'0'),
(5, 4, 2, 'common.action.search', '查询', b'1', 'CRUD:ARCO:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 5, now(), '1698900184022351875', NULL, NULL, b'0'),
(6, 4, 2, 'common.action.export', '导出', b'1', 'CRUD:ARCO:EXPORT', NULL, NULL, NULL, NULL, NULL, NULL, 6, now(), '1698900184022351875', NULL, NULL, b'0'),
(7, 4, 2, 'common.action.delete', '删除', b'1', 'CRUD:ARCO:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 7, now(), '1698900184022351875', NULL, NULL, b'0'),
(8, 4, 2, 'common.action.batch.delete', '批量删除', b'1', 'CRUD:ARCO:BATCH:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 8, now(), '1698900184022351875', NULL, NULL, b'0'),
(9, 3, 1, 'menu.crud.arco.add', '原生模式新增', b'1', 'CRUD:ARCO:ADD', 'CrudArcoAdd', 'crud-arco-add', NULL, b'1', b'0', b'0', 9, now(), '1698900184022351875', NULL, NULL, b'0'),
(10, 3, 1, 'menu.crud.arco.update', '原生模式编辑', b'1', 'CRUD:ARCO:UPDATE', 'CrudArcoUpdate', 'crud-arco-update', NULL, b'1', b'0', b'0', 10, now(), '1698900184022351875', NULL, NULL, b'0'),
(11, 3, 1, 'menu.crud.arco.upload', '原生模式导入', b'1', 'CRUD:ARCO:UPLOAD', 'CrudArcoUpload', 'crud-arco-upload', NULL, b'1', b'0', b'0', 11, now(), '1698900184022351875', NULL, NULL, b'0'),
(12, 3, 1, 'menu.crud.components', '组件模式', b'1', 'CRUD:COMPONENTS', 'CrudComponents', 'crud-components', NULL, b'0', b'0', b'0', 12, now(), '1698900184022351875', NULL, NULL, b'0'),
(13, 12, 2, 'common.action.search', '查询', b'1', 'CRUD:COMPONENTS:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 13, now(), '1698900184022351875', NULL, NULL, b'0'),
(14, 12, 2, 'common.action.export', '导出', b'1', 'CRUD:COMPONENTS:EXPORT', NULL, NULL, NULL, NULL, NULL, NULL, 14, now(), '1698900184022351875', NULL, NULL, b'0'),
(15, 12, 2, 'common.action.delete', '删除', b'1', 'CRUD:COMPONENTS:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 15, now(), '1698900184022351875', NULL, NULL, b'0'),
(16, 12, 2, 'common.action.batch.delete', '批量删除', b'1', 'CRUD:COMPONENTS:BATCH:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 16, now(), '1698900184022351875', NULL, NULL, b'0'),
(17, 3, 1, 'menu.crud.components.add', '组件模式新增', b'1', 'CRUD:COMPONENTS:ADD', 'CrudComponentsAdd', 'crud-components-add', NULL, b'1', b'0', b'0', 17, now(), '1698900184022351875', NULL, NULL, b'0'),
(18, 3, 1, 'menu.crud.components.update', '组件模式编辑', b'1', 'CRUD:COMPONENTS:UPDATE', 'CrudComponentsUpdate', 'crud-components-update', NULL, b'1', b'0', b'0', 18, now(), '1698900184022351875', NULL, NULL, b'0'),
(19, 3, 1, 'menu.crud.components.upload', '组件模式导入', b'1', 'CRUD:COMPONENTS:UPLOAD', 'CrudComponentsUpload', 'crud-components-upload', NULL, b'1', b'0', b'0', 19, now(), '1698900184022351875', NULL, NULL, b'0'),
(20, 3, 1, 'menu.crud.gen', '脚手架模式', b'1', 'CRUD:GEN', 'CrudGen', 'crud-gen', NULL, b'0', b'0', b'0', 20, now(), '1698900184022351875', NULL, NULL, b'0'),
(21, 20, 2, 'common.action.search', '查询', b'1', 'CRUD:GEN:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 21, now(), '1698900184022351875', NULL, NULL, b'0'),
(22, 20, 2, 'common.action.export', '导出', b'1', 'CRUD:GEN:EXPORT', NULL, NULL, NULL, NULL, NULL, NULL, 22, now(), '1698900184022351875', NULL, NULL, b'0'),
(23, 20, 2, 'common.action.delete', '删除', b'1', 'CRUD:GEN:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 23, now(), '1698900184022351875', NULL, NULL, b'0'),
(24, 20, 2, 'common.action.batch.delete', '批量删除', b'1', 'CRUD:GEN:BATCH:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 24, now(), '1698900184022351875', NULL, NULL, b'0'),
(25, 3, 1, 'menu.crud.gen.add', '脚手架模式新增', b'1', 'CRUD:GEN:ADD', 'CrudGenAdd', 'crud-gen-add', NULL, b'1', b'0', b'0', 25, now(), '1698900184022351875', NULL, NULL, b'0'),
(26, 3, 1, 'menu.crud.gen.update', '脚手架模式编辑', b'1', 'CRUD:GEN:UPDATE', 'CrudGenUpdate', 'crud-gen-update', NULL, b'1', b'0', b'0', 26, now(), '1698900184022351875', NULL, NULL, b'0'),
(27, 3, 1, 'menu.crud.gen.upload', '脚手架模式导入', b'1', 'CRUD:GEN:UPLOAD', 'CrudGenUpload', 'crud-gen-upload', NULL, b'1', b'0', b'0', 27, now(), '1698900184022351875', NULL, NULL, b'0'),
(28, 0, 1, 'menu.basic', '基础样例', b'1', 'BASIC', 'Basic', '/basic', 'icon-save', NULL, NULL, NULL, 28, now(), '1698900184022351875', NULL, NULL, b'0'),
(29, 28, 1, 'menu.basic.http', 'http样例', b'1', 'HTTP', 'Http', 'http', NULL, b'0', b'0', b'0', 29, now(), '1698900184022351875', NULL, NULL, b'0'),
(30, 28, 1, 'menu.basic.concurrent', '线程池样例', b'1', 'CONCURRENT', 'Concurrent', 'concurrent', NULL, b'0', b'0', b'0', 30, now(), '1698900184022351875', NULL, NULL, b'0'),
(31, 0, 1, 'menu.ware', '中间件', b'1', 'WARE', 'Ware', '/ware', 'icon-relation', NULL, NULL, NULL, 31, now(), '1698900184022351875', NULL, NULL, b'0'),
(32, 31, 1, 'menu.ware.redis', 'Redis', b'1', 'REDIS', 'Redis', 'redis', NULL, b'0', b'0', b'0', 32, now(), '1698900184022351875', NULL, NULL, b'0'),
(33, 32, 2, 'common.action.search', '查询', b'1', 'REDIS:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 33, now(), '1698900184022351875', NULL, NULL, b'0'),
(34, 32, 2, 'common.action.info', '详情', b'1', 'REDIS:INFO', NULL, NULL, NULL, NULL, NULL, NULL, 34, now(), '1698900184022351875', NULL, NULL, b'0'),
(35, 32, 2, 'common.action.add', '新增', b'1', 'REDIS:ADD', NULL, NULL, NULL, NULL, NULL, NULL, 35, now(), '1698900184022351875', NULL, NULL, b'0'),
(36, 32, 2, 'common.action.update', '编辑', b'1', 'REDIS:UPDATE', NULL, NULL, NULL, NULL, NULL, NULL, 36, now(), '1698900184022351875', NULL, NULL, b'0'),
(37, 32, 2, 'ware.redis.expire.update', '编辑有效期', b'1', 'REDIS:EXPIRE', NULL, NULL, NULL, NULL, NULL, NULL, 37, now(), '1698900184022351875', NULL, NULL, b'0'),
(38, 32, 2, 'common.action.delete', '删除', b'1', 'REDIS:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 38, now(), '1698900184022351875', NULL, NULL, b'0'),
(39, 31, 1, 'menu.ware.mq', '消息队列', b'1', 'MQ', 'Mq', 'mq', NULL, b'0', b'0', b'0', 39, now(), '1698900184022351875', NULL, NULL, b'0'),
(40, 0, 1, 'menu.system', '系统管理', b'1', 'SYSTEM', 'System', '/system', 'icon-settings', NULL, NULL, NULL, 40, now(), '1698900184022351875', NULL, NULL, b'0'),
(41, 40, 1, 'menu.system.tenant', '集团管理', b'1', 'TENANT', 'Tenant', 'tenant', NULL, b'0', b'0', b'0', 41, now(), '1698900184022351875', NULL, NULL, b'0'),
(42, 41, 2, 'common.action.search', '查询', b'1', 'TENANT:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 42, now(), '1698900184022351875', NULL, NULL, b'0'),
(43, 41, 2, 'common.action.info', '详情', b'1', 'TENANT:INFO', NULL, NULL, NULL, NULL, NULL, NULL, 43, now(), '1698900184022351875', NULL, NULL, b'0'),
(44, 41, 2, 'system.tenant.admin.password.copy', '复制管理员密码', b'1', 'TENANT:PASSWORD', NULL, NULL, NULL, NULL, NULL, NULL, 44, now(), '1698900184022351875', NULL, NULL, b'0'),
(45, 41, 2, 'common.action.add', '新增', b'1', 'TENANT:ADD', NULL, NULL, NULL, NULL, NULL, NULL, 45, now(), '1698900184022351875', NULL, NULL, b'0'),
(46, 41, 2, 'common.action.update', '编辑', b'1', 'TENANT:UPDATE', NULL, NULL, NULL, NULL, NULL, NULL, 46, now(), '1698900184022351875', NULL, NULL, b'0'),
(47, 41, 2, 'common.action.delete', '删除', b'1', 'TENANT:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 47, now(), '1698900184022351875', NULL, NULL, b'0'),
(48, 41, 2, 'common.action.batch.delete', '批量删除', b'1', 'TENANT:BATCH:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 48, now(), '1698900184022351875', NULL, NULL, b'0'),
(49, 40, 1, 'menu.system.menu', '菜单管理', b'1', 'MENU', 'SystemMenu', 'menu', NULL, b'0', b'0', b'0', 49, now(), '1698900184022351875', NULL, NULL, b'0'),
(50, 49, 2, 'common.action.search', '查询', b'1', 'MENU:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 50, now(), '1698900184022351875', NULL, NULL, b'0'),
(51, 40, 1, 'menu.system.user', '用户管理', b'1', 'USER', 'User', 'user', NULL, b'0', b'0', b'0', 51, now(), '1698900184022351875', NULL, NULL, b'0'),
(52, 51, 2, 'common.action.search', '查询', b'1', 'USER:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 52, now(), '1698900184022351875', NULL, NULL, b'0'),
(53, 51, 2, 'system.user.password.copy', '复制密码', b'1', 'USER:PASSWORD', NULL, NULL, NULL, NULL, NULL, NULL, 53, now(), '1698900184022351875', NULL, NULL, b'0'),
(54, 51, 2, 'common.action.batch', '批量操作', b'1', 'USER:STATE:ACTIVATION', NULL, NULL, NULL, NULL, NULL, NULL, 54, now(), '1698900184022351875', NULL, NULL, b'0'),
(55, 40, 1, 'menu.system.user.info', '用户详情', b'1', 'USER:INFO', 'UserInfo', 'user-info', NULL, b'1', b'0', b'0', 55, now(), '1698900184022351875', NULL, NULL, b'0'),
(56, 40, 1, 'menu.system.user.add', '用户新增', b'1', 'USER:ADD', 'UserAdd', 'user-add', NULL, b'1', b'0', b'0', 56, now(), '1698900184022351875', NULL, NULL, b'0'),
(57, 40, 1, 'menu.system.user.update', '用户编辑', b'1', 'USER:UPDATE', 'UserUpdate', 'user-update', NULL, b'1', b'0', b'0', 57, now(), '1698900184022351875', NULL, NULL, b'0'),
(58, 40, 1, 'menu.system.role', '角色管理', b'1', 'ROLE', 'Role', 'role', NULL, b'0', b'0', b'0', 58, now(), '1698900184022351875', NULL, NULL, b'0'),
(59, 58, 2, 'common.action.search', '查询', b'1', 'ROLE:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 59, now(), '1698900184022351875', NULL, NULL, b'0'),
(60, 58, 2, 'common.action.info', '详情', b'1', 'ROLE:INFO', NULL, NULL, NULL, NULL, NULL, NULL, 60, now(), '1698900184022351875', NULL, NULL, b'0'),
(61, 40, 1, 'menu.system.role.add', '角色新增', b'1', 'ROLE:ADD', 'RoleAdd', 'role-add', NULL, b'1', b'0', b'0', 61, now(), '1698900184022351875', NULL, NULL, b'0'),
(62, 40, 1, 'menu.system.role.update', '角色编辑', b'1', 'ROLE:UPDATE', 'RoleUpdate', 'role-update', NULL, b'1', b'0', b'0', 62, now(), '1698900184022351875', NULL, NULL, b'0'),
(63, 40, 1, 'menu.system.notice', '通知管理', b'1', 'NOTICE', 'Notice', 'notice', NULL, b'0', b'0', b'0', 63, now(), '1698900184022351875', NULL, NULL, b'0'),
(64, 63, 2, 'common.action.search', '查询', b'1', 'NOTICE:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 64, now(), '1698900184022351875', NULL, NULL, b'0'),
(65, 63, 2, 'common.action.info', '详情', b'1', 'NOTICE:INFO', NULL, NULL, NULL, NULL, NULL, NULL, 65, now(), '1698900184022351875', NULL, NULL, b'0'),
(66, 63, 2, 'common.action.send', '发送', b'1', 'NOTICE:SEND', NULL, NULL, NULL, NULL, NULL, NULL, 66, now(), '1698900184022351875', NULL, NULL, b'0'),
(67, 63, 2, 'common.action.delete', '删除', b'1', 'NOTICE:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 67, now(), '1698900184022351875', NULL, NULL, b'0'),
(68, 63, 2, 'common.action.batch.delete', '批量删除', b'1', 'NOTICE:BATCH:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 68, now(), '1698900184022351875', NULL, NULL, b'0'),
(69, 63, 2, 'common.action.info.receive', '接收情况', b'1', 'NOTICE:USER', NULL, NULL, NULL, NULL, NULL, NULL, 69, now(), '1698900184022351875', NULL, NULL, b'0'),
(70, 40, 1, 'menu.system.notice.add', '通知新增', b'1', 'NOTICE:ADD', 'NoticeAdd', 'notice-add', NULL, b'1', b'0', b'0', 70, now(), '1698900184022351875', NULL, NULL, b'0'),
(71, 40, 1, 'menu.system.notice.update', '通知编辑', b'1', 'NOTICE:UPDATE', 'NoticeUpdate', 'notice-update', NULL, b'1', b'0', b'0', 71, now(), '1698900184022351875', NULL, NULL, b'0'),
(72, 0, 1, 'menu.dev', '开发工具', b'1', 'DEV', 'Dev', '/dev', 'icon-code', NULL, NULL, NULL, 72, now(), '1698900184022351875', NULL, NULL, b'0'),
(73, 72, 1, 'menu.dev.log.request', '请求日志', b'1', 'LOG:REQUEST', 'LogRequest', 'log-request', NULL, b'0', b'0', b'0', 73, now(), '1698900184022351875', NULL, NULL, b'0'),
(74, 73, 2, 'common.action.search', '查询', b'1', 'LOG:REQUEST:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 74, now(), '1698900184022351875', NULL, NULL, b'0'),
(75, 73, 2, 'common.action.info', '详情', b'1', 'LOG:REQUEST:INFO', NULL, NULL, NULL, NULL, NULL, NULL, 75, now(), '1698900184022351875', NULL, NULL, b'0'),
(76, 72, 1, 'menu.dev.dict', '字典管理', b'1', 'DICT', 'Dict', 'dict', NULL, b'0', b'0', b'0', 76, now(), '1698900184022351875', NULL, NULL, b'0'),
(77, 76, 2, 'common.action.search', '查询', b'1', 'DICT:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 77, now(), '1698900184022351875', NULL, NULL, b'0'),
(78, 76, 2, 'common.action.refresh.cache', '刷新缓存', b'1', 'DICT:REFRESH:CACHE', NULL, NULL, NULL, NULL, NULL, NULL, 78, now(), '1698900184022351875', NULL, NULL, b'0'),
(79, 76, 2, 'common.action.add.dict', '新增字典', b'1', 'DICT:ADD', NULL, NULL, NULL, NULL, NULL, NULL, 79, now(), '1698900184022351875', NULL, NULL, b'0'),
(80, 76, 2, 'common.action.update.dict', '编辑字典', b'1', 'DICT:UPDATE', NULL, NULL, NULL, NULL, NULL, NULL, 80, now(), '1698900184022351875', NULL, NULL, b'0'),
(81, 76, 2, 'common.action.delete.dict', '删除字典', b'1', 'DICT:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 81, now(), '1698900184022351875', NULL, NULL, b'0'),
(82, 76, 2, 'common.action.info.dict.item', '查看字典项', b'1', 'DICT:ITEM:TREE', NULL, NULL, NULL, NULL, NULL, NULL, 82, now(), '1698900184022351875', NULL, NULL, b'0'),
(83, 76, 2, 'common.action.add.dict.item', '新增字典项', b'1', 'DICT:ITEM:ADD', NULL, NULL, NULL, NULL, NULL, NULL, 83, now(), '1698900184022351875', NULL, NULL, b'0'),
(84, 76, 2, 'common.action.update.dict.item', '编辑字典项', b'1', 'DICT:ITEM:UPDATE', NULL, NULL, NULL, NULL, NULL, NULL, 84, now(), '1698900184022351875', NULL, NULL, b'0'),
(85, 76, 2, 'common.action.delete.dict.item', '删除字典项', b'1', 'DICT:ITEM:DELETE', NULL, NULL, NULL, NULL, NULL, NULL, 85, now(), '1698900184022351875', NULL, NULL, b'0'),
(86, 72, 1, 'menu.dev.gen', '代码生成', b'1', 'GEN', 'Gen', 'gen', NULL, b'0', b'0', b'0', 86, now(), '1698900184022351875', NULL, NULL, b'0'),
(87, 86, 2, 'common.action.search', '查询', b'1', 'GEN:QUERY', NULL, NULL, NULL, NULL, NULL, NULL, 87, now(), '1698900184022351875', NULL, NULL, b'0'),
(88, 72, 1, 'menu.dev.gen.operate', '生成', b'1', 'GEN:OPERATE', 'GenOperate', 'gen-operate', NULL, b'1', b'0', b'0', 88, now(), '1698900184022351875', NULL, NULL, b'0'),
(89, 72, 1, 'menu.dev.empty', '空页面', b'1', 'DEV:EMPTY', 'Empty', 'empty', NULL, b'0', b'0', b'0', 89, now(), '1698900184022351875', NULL, NULL, b'0'),
(90, 0, 1, 'menu.center', '个人中心', b'1', 'CENTER', 'Center', '/center', 'icon-user', NULL, NULL, NULL, 90, now(), '1698900184022351875', NULL, NULL, b'0'),
(91, 90, 1, 'menu.center.userPersonal', '个人信息', b'0', '', 'UserPersonal', 'user-personal', NULL, b'0', b'0', b'0', 91, now(), '1698900184022351875', NULL, NULL, b'0'),
(92, 90, 1, 'menu.center.notice', '个人通知', b'0', '', 'NoticeUser', 'notice-user', NULL, b'0', b'0', b'0', 92, now(), '1698900184022351875', NULL, NULL, b'0'),
(93, 90, 1, 'menu.center.notice.info', '通知详情', b'0', '', 'NoticeUserInfo', 'notice-user-info', NULL, b'1', b'0', b'0', 93, now(), '1698900184022351875', NULL, NULL, b'0'),
(94, 0, 1, 'menu.external', '外部链接', b'1', 'EXTERNAL', 'External', '/external', 'icon-link', NULL, NULL, NULL, 94, now(), '1698900184022351875', NULL, NULL, b'0'),
(95, 94, 1, 'menu.arcoWebsite', 'Arco Design', b'1', 'ARCOWEBSITE', 'ArcoWebsite', 'https://arco.design', '', NULL, NULL, NULL, 95, now(), '1698900184022351875', NULL, NULL, b'0'),
(96, 94, 1, 'menu.jerryUpBlog', 'Jerry Up Blog', b'1', 'JERRYUPBLOG', 'JerryUpBlog', 'https://jerry-up-blog.pages.dev/', '', NULL, NULL, NULL, 96, now(), '1698900184022351875', NULL, NULL, b'0');

update `lala_boot`.`sys_menu` set menu_order = id;

DROP TABLE IF EXISTS `lala_boot`.`sys_dict`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `dict_name` varchar(50) NOT NULL COMMENT '字典名称',
  `dict_key` varchar(50) NOT NULL COMMENT '字典标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_dict_unique_key` (`dict_key`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = utf8mb4 COMMENT = '字典类型表';

insert into `lala_boot`.`sys_dict`(`id`, `dict_name`, `dict_key`, `create_time`, `create_user`) values 
(1, '状态', 'STATE', now(), '1698900184022351875'),
(2, 'crud列表', 'CRUD_LIST', now(), '1698900184022351875'),
(3, 'crud树', 'CRUD_TREE', now(), '1698900184022351875');

DROP TABLE IF EXISTS `lala_boot`.`sys_dict_item`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`sys_dict_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典值ID',
  `dict_id` bigint(20) NOT NULL COMMENT '字典类型ID(sys_dict.id)',
  `parent_id` bigint(20) NOT NULL COMMENT '字典父ID',
  `label` varchar(50) NOT NULL COMMENT '展示名',
  `value` varchar(50) COMMENT '值',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序号(值越高，越靠后)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`),
  KEY `sys_dict_item_key` (`dict_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典项';

insert into `lala_boot`.`sys_dict_item`(`id`, `dict_id`, `parent_id`, `label`, `value`, `sort_order`, `create_time`, `create_user`) values 
(1, 1, 0, '启用', 'true', 1, now(), '1698900184022351875'),
(2, 1, 0, '停用', 'false', 2, now(), '1698900184022351875'),
(3, 2, 0, '编码', '1', 1, now(), '1698900184022351875'),
(4, 2, 0, '足球', '2', 2, now(), '1698900184022351875'),
(5, 2, 0, 'F1', '3', 3, now(), '1698900184022351875'),
(6, 3, 0, '编码', '1', 1, now(), '1698900184022351875'),
(7, 3, 0, '足球', '2', 2, now(), '1698900184022351875'),
(8, 3, 0, 'F1', '3', 3, now(), '1698900184022351875'),
(9, 3, 6, 'Java', '4', 4, now(), '1698900184022351875'),
(10, 3, 6, 'Go', '5', 5, now(), '1698900184022351875'),
(11, 3, 6, 'Node.js', '6', 6, now(), '1698900184022351875'),
(12, 3, 7, '皇马', '7', 7, now(), '1698900184022351875'),
(13, 3, 7, '曼联', '8', 8, now(), '1698900184022351875'),
(14, 3, 7, '里斯本竞技', '9', 9, now(), '1698900184022351875'),
(15, 3, 8, '阿斯顿马丁', '10', 10, now(), '1698900184022351875'),
(16, 3, 8, '红牛', '11', 11, now(), '1698900184022351875'),
(17, 3, 8, '梅赛德斯AMG', '12', 12, now(), '1698900184022351875');

DROP TABLE IF EXISTS `lala_boot`.`user`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`user` (
  `id` varchar(50) NOT NULL COMMENT '账号ID',
  `tenant_id` varchar(50) DEFAULT NULL COMMENT '集团ID',
  `login_name` varchar(50) NOT NULL COMMENT '账号(集团唯一)',
  `pass_word` varchar(512) NOT NULL COMMENT '用户密码',
  `real_name` varchar(50) NOT NULL COMMENT '姓名',
  `mail` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `intro` varchar(255) DEFAULT NULL COMMENT '个人简介',
  `state` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态[true-启用]',
  `tenant_admin` bit(1) NOT NULL DEFAULT b'1' COMMENT '集团管理员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '账号表';

-- 密码: lala
INSERT INTO `lala_boot`.`user` (`id`, `tenant_id`, `login_name`, `pass_word`, `real_name`, `state`, `tenant_admin`, `create_time`, `create_user`) VALUES 
('1698900184022351877', '1698900184022351876', 'tenant', '8076a8ca0144c8bcaf2b064961132ccb0b291c7b75622687330cbb53c3a33dbc6751fb6e6eb73a5e1dc0c16e5946f8cbddb705950b93edfe2afb99020b69ab10c0c10b769ae36a465a6fabfeab3da2c7ab88aa1b2d08e1c209d957fdc6cfec5bb67def365e6bf62ed374ef3cad4174f4f01b2b1e94391d7fd90aa7188defab26',
'集团管理员', b'1', b'1', now(), '1698900184022351875'),
('1698900184022351878', '1698900184022351876', 'demo', '8076a8ca0144c8bcaf2b064961132ccb0b291c7b75622687330cbb53c3a33dbc6751fb6e6eb73a5e1dc0c16e5946f8cbddb705950b93edfe2afb99020b69ab10c0c10b769ae36a465a6fabfeab3da2c7ab88aa1b2d08e1c209d957fdc6cfec5bb67def365e6bf62ed374ef3cad4174f4f01b2b1e94391d7fd90aa7188defab26',
'普通账号', b'1', b'0', now(), '1698900184022351875');

DROP TABLE IF EXISTS `lala_boot`.`user_setting`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`user_setting` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `tenant_id` varchar(50) DEFAULT NULL COMMENT '集团ID',
  `user_id` varchar(50) DEFAULT NULL COMMENT '账号ID【user.id】',
  `theme` varchar(50) DEFAULT NULL COMMENT '主题(light/dark)',
  `navbar` bit(1) DEFAULT NULL COMMENT '导航栏',
  `menu` bit(1) DEFAULT NULL COMMENT '菜单栏',
  `top_menu` bit(1) DEFAULT NULL COMMENT '顶部菜单栏',
  `menu_collapse` bit(1) DEFAULT NULL COMMENT '菜单折叠',
  `menu_width` int(11) DEFAULT NULL COMMENT '菜单宽度(px)',
  `footer` bit(1) DEFAULT NULL COMMENT '底部',
  `tab_bar` bit(1) DEFAULT NULL COMMENT '多页签',
  `color_weak` bit(1) DEFAULT NULL COMMENT '色弱模式',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`),
  KEY `user_setting` (`user_id`) USING HASH
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '账号配置表';

INSERT INTO `lala_boot`.`user_setting` (`tenant_id`, `user_id`, `theme`, `navbar`, `menu`, `top_menu`, `menu_collapse`, `menu_width`, `footer`, `tab_bar`, `color_weak`, 
`create_time`, `create_user`) VALUES 
('1698900184022351876', '1698900184022351877', 'light', b'1', b'1', b'0', b'0', 220, b'1', b'1', b'0', now(), '1698900184022351875'),
('1698900184022351876', '1698900184022351878', 'light', b'1', b'1', b'0', b'0', 220, b'1', b'1', b'0', now(), '1698900184022351875');

DROP TABLE IF EXISTS `lala_boot`.`role`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `tenant_id` varchar(50) DEFAULT NULL COMMENT '集团ID',
  `role_name` varchar(30) NOT NULL DEFAULT '' COMMENT '角色名称',
  `description` varchar(200) DEFAULT '' COMMENT '描述',
  `state` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态[true-启用]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`),
  KEY `role` (`tenant_id`) USING HASH
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '角色表';

INSERT INTO `lala_boot`.`role` (`id`, `tenant_id`, `role_name`, `description`, `state`, `create_time`, `create_user`) VALUES 
(1, '1698900184022351876', '默认角色', '系统初始化角色', b'1', now(), '1698900184022351875');

DROP TABLE IF EXISTS `lala_boot`.`role_menu`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`role_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色菜单ID',
  `tenant_id` varchar(50) DEFAULT NULL COMMENT '集团ID',
  `role_id` bigint(20) NOT NULL COMMENT 'role.id',
  `menu_id` bigint(20) NOT NULL COMMENT 'sys_menu.id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`),
  KEY `role_menu` (`role_id`, `menu_id`) USING HASH
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '角色绑定菜单表';

INSERT INTO `lala_boot`.`role_menu` (`tenant_id`, `role_id`, `menu_id`, `create_time`, `create_user`) 
select '1698900184022351876', 1, id, now(), '1698900184022351875' from `lala_boot`.`sys_menu`;

DROP TABLE IF EXISTS `lala_boot`.`role_user`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`role_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色账号ID',
  `tenant_id` varchar(50) DEFAULT NULL COMMENT '集团ID',
  `role_id` bigint(20) NOT NULL COMMENT 'role.id',
  `user_id` varchar(255) NOT NULL COMMENT 'user.id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`),
  KEY `role_user` (`role_id`, `user_id`) USING HASH
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '角色绑定账号表';

INSERT INTO `lala_boot`.`role_user` (`tenant_id`, `role_id`, `user_id`, `create_time`, `create_user`) VALUES 
('1698900184022351876', 1, '1698900184022351878', now(), '1698900184022351875');

DROP TABLE IF EXISTS `lala_boot`.`crud`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`crud` (
  `id` varchar(20) NOT NULL COMMENT '主键ID',
  `tenant_id` varchar(50) DEFAULT NULL COMMENT '集团ID',
  `input` varchar(50) DEFAULT NULL COMMENT '输入框',
  `input_number` DECIMAL(25, 2) DEFAULT NULL COMMENT '数字输入框',
  `input_tags` varchar(50) DEFAULT NULL COMMENT '标签输入框',
  `auto_complete` varchar(50) DEFAULT NULL COMMENT '自动补全',
  `mention` varchar(50) DEFAULT NULL COMMENT '提及',
  `text_area` varchar(255) DEFAULT NULL COMMENT '文本域',
  `rate` DECIMAL(25, 2) DEFAULT NULL COMMENT '评分',
  `slider` int(11) DEFAULT NULL COMMENT '滑动输入条',
  `switch_info` varchar(50) DEFAULT NULL COMMENT '开关(STATE),单选',
  `radio` varchar(50) DEFAULT NULL COMMENT '单选框(CRUD_LIST),单选',
  `checkboxes` varchar(255) DEFAULT NULL COMMENT '复选框(CRUD_LIST),多选',
  `select_info` varchar(255) DEFAULT NULL COMMENT '选择器(CRUD_LIST),单选',
  `cascader` varchar(255) DEFAULT NULL COMMENT '级联选择(CRUD_TREE),单选',
  `tree_select` varchar(255) DEFAULT NULL COMMENT '树选择(CRUD_TREE),单选',
  `transfers` varchar(255) DEFAULT NULL COMMENT '数据穿梭框(CRUD_LIST),多选',
  `date_time_picker` datetime DEFAULT NULL COMMENT '日期时间选择器',
  `upload` varchar(255) DEFAULT NULL COMMENT '上传',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET = utf8mb4 COMMENT='crud表';

DROP TABLE IF EXISTS `lala_boot`.`notice`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`notice` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `tenant_id` varchar(50) DEFAULT NULL COMMENT '集团ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `html` text NOT NULL COMMENT '通知内容',
  `notice_type` int(11) NOT NULL COMMENT '类型【1-通知，2-公告】',
  `send_state` int(11) NOT NULL COMMENT '发送状态【0-未发送，1-已发送】',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '通知表';

DROP TABLE IF EXISTS `lala_boot`.`notice_user`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`notice_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `tenant_id` varchar(50) DEFAULT NULL COMMENT '集团ID',
  `notice_id` bigint(20) unsigned NOT NULL COMMENT 'notice.id',
  `user_id` varchar(255) NOT NULL COMMENT 'user.id',
  `read_state` int(11) NOT NULL COMMENT '阅读状态【0-未发送，1-未读，2-已发送】',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`),
  KEY `notice_user` (`notice_id`) USING HASH
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '通知记录表';

-- 其他数据隔离模式

DROP TABLE IF EXISTS `lala_boot`.`sys_data_source`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`sys_data_source` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(50) NOT NULL COMMENT '集团ID',
  `ip` varchar(512) NOT NULL COMMENT '数据库IP',
  `port` varchar(50) NOT NULL COMMENT '数据库端口',
  `user_name` varchar(50) NOT NULL COMMENT '数据库用户名',
  `pass_word` varchar(50) NOT NULL COMMENT '数据库密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '数据源表';

INSERT INTO `lala_boot`.`sys_data_source` (`tenant_id`, `ip`, `port`, `user_name`, `pass_word`, `create_time`) VALUES 
('1698900184022351876', '127.0.0.1', '3306', 'root', '123456', now());

DROP TABLE IF EXISTS `lala_boot`.`user_1698900184022351876`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`user_1698900184022351876` (
  `id` varchar(50) NOT NULL COMMENT '账号ID',
  `login_name` varchar(50) NOT NULL COMMENT '账号(集团唯一)',
  `pass_word` varchar(512) NOT NULL COMMENT '用户密码',
  `real_name` varchar(50) NOT NULL COMMENT '姓名',
  `state` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态[true-启用]',
  `tenant_admin` bit(1) NOT NULL DEFAULT b'1' COMMENT '集团管理员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '账号表';

INSERT INTO `lala_boot`.`user_1698900184022351876` (`id`, `login_name`, `pass_word`, `real_name`, `state`, `tenant_admin`, `create_time`, `create_user`) VALUES 
('1698900184022351876', 'tenant', '8076a8ca0144c8bcaf2b064961132ccb0b291c7b75622687330cbb53c3a33dbc6751fb6e6eb73a5e1dc0c16e5946f8cbddb705950b93edfe2afb99020b69ab10c0c10b769ae36a465a6fabfeab3da2c7ab88aa1b2d08e1c209d957fdc6cfec5bb67def365e6bf62ed374ef3cad4174f4f01b2b1e94391d7fd90aa7188defab26',
'集团管理员', b'1', b'1', now(), '1698900184022351875');

DROP TABLE IF EXISTS `lala_boot`.`crud_1698900184022351876`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`crud_1698900184022351876` (
  `id` varchar(20) NOT NULL COMMENT '主键ID',
  `input` varchar(50) DEFAULT NULL COMMENT '输入框',
  `input_number` DECIMAL(25, 2) DEFAULT NULL COMMENT '数字输入框',
  `input_tags` varchar(50) DEFAULT NULL COMMENT '标签输入框',
  `auto_complete` varchar(50) DEFAULT NULL COMMENT '自动补全',
  `mention` varchar(50) DEFAULT NULL COMMENT '提及',
  `text_area` varchar(255) DEFAULT NULL COMMENT '文本域',
  `rate` DECIMAL(25, 2) DEFAULT NULL COMMENT '评分',
  `slider` int(11) DEFAULT NULL COMMENT '滑动输入条',
  `switch_info` varchar(50) DEFAULT NULL COMMENT '开关(STATE),单选',
  `radio` varchar(50) DEFAULT NULL COMMENT '单选框(CRUD_LIST),单选',
  `checkboxes` varchar(255) DEFAULT NULL COMMENT '复选框(CRUD_LIST),多选',
  `select_info` varchar(255) DEFAULT NULL COMMENT '选择器(CRUD_LIST),单选',
  `cascader` varchar(255) DEFAULT NULL COMMENT '级联选择(CRUD_TREE),单选',
  `tree_select` varchar(255) DEFAULT NULL COMMENT '树选择(CRUD_TREE),单选',
  `transfers` varchar(255) DEFAULT NULL COMMENT '数据穿梭框(CRUD_LIST),多选',
  `date_time_picker` datetime DEFAULT NULL COMMENT '日期时间选择器',
  `upload` varchar(255) DEFAULT NULL COMMENT '上传',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET = utf8mb4 COMMENT='crud表';

DROP TABLE IF EXISTS `lala_boot`.`role_1698900184022351876`;
CREATE TABLE IF NOT EXISTS `lala_boot`.`role_1698900184022351876` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL DEFAULT '' COMMENT '角色名称',
  `description` varchar(200) DEFAULT '' COMMENT '描述',
  `state` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态[true-启用]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime COMMENT '修改时间',
  `update_user` varchar(255) COMMENT '修改人',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除【1-删除】',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '角色表';
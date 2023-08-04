use dazhou;
-- 接口信息表
create table dazhou.`interface_info`
(
    `id` bigint not null auto_increment comment '用户id(主键)' primary key,
    `name` varchar(256) not null comment '名称',
    `description` varchar(256) null comment '描述',
    `url` varchar(512) not null comment '接口地址',
    `requestParams` text not null  comment '请求参数',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `status` int default 0 not null comment '接口状态(0-关闭,1-开启)',
    `method` varchar(256) not null comment '请求类型',
    `userId` bigint not null comment '创建人',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
    ) comment '接口信息表';

insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('李哲瀚', 'Fnpo', 'www.hiram-collier.name', '范哲瀚', '吴建辉', 0, '武烨华', 363602362);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('郭昊强', 'RYp', 'www.junior-ruecker.com', '曹果', '罗明哲', 0, '彭浩宇', 288951761);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('郑文昊', 'vtv', 'www.tempie-runte.name', '赖语堂', '陈彬', 0, '莫瑞霖', 1);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('何耀杰', 'NbAm6', 'www.marcos-gutmann.org', '韩鹤轩', '姚彬', 0, '戴瑞霖', 869496973);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('张语堂', 'cm', 'www.lakendra-beer.biz', '毛天宇', '卢思淼', 0, '萧荣轩', 5);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('曹乐驹', 'oPtLP', 'www.mafalda-von.io', '钟伟祺', '丁皓轩', 0, '毛钰轩', 617813);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('任思', 'mO', 'www.genaro-blick.biz', '胡航', '余天磊', 0, '赖彬', 98415975);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('苏思淼', 'l5', 'www.dana-thiel.biz', '钱浩', '沈鑫鹏', 0, '任伟宸', 937);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('张熠彤', 'VKSP', 'www.douglass-olson.net', '崔雪松', '武鑫磊', 0, '万昊天', 9154761);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('熊晓啸', 'DZy', 'www.morton-beahan.net', '贾钰轩', '龙煜城', 0, '张涛', 49);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('夏煜祺', 'mqfdY', 'www.ellis-romaguera.name', '谭修洁', '秦烨霖', 0, '董鑫磊', 450);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('孙煜祺', 'Dpd', 'www.nick-effertz.biz', '何立诚', '薛笑愚', 0, '苏鑫磊', 642804336);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('钱鹏', 'CjI7', 'www.bobby-collins.info', '杜晓啸', '邵晓啸', 0, '莫哲瀚', 447);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('覃擎宇', 'vB', 'www.odell-ferry.net', '吕楷瑞', '沈雨泽', 0, '洪伟祺', 8680);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('张伟宸', '8wnA', 'www.dalia-osinski.com', '金煜城', '胡煜城', 0, '刘炎彬', 3507959949);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('沈瑾瑜', 'bPkb', 'www.josiah-weimann.info', '朱鑫磊', '陈峻熙', 0, '孔志泽', 8241);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('徐峻熙', 'qz8c', 'www.justin-kunde.biz', '黄博超', '高越泽', 0, '程立诚', 9);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('白浩宇', 'pdq', 'www.earlean-koelpin.info', '卢烨华', '程立辉', 0, '蔡立诚', 19);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('刘梓晨', 'IW', 'www.wilber-emmerich.net', '覃思聪', '陶鑫鹏', 0, '罗子默', 4);
insert into dazhou.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('韩凯瑞', 'rybN', 'www.john-moore.org', '冯文昊', '何伟诚', 0, '任思远', 424967771);

-- 用户调用接口关系表
create table dazhou.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户id',
    `interfaceInfoId` bigint not null comment '接口id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常,1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系表';
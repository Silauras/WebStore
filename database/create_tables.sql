create table category
(
    category_id     bigint auto_increment
        primary key,
    name            varchar(50) not null,
    parent_category bigint      null,
    constraint category_ibfk_1
        foreign key (parent_category) references category (category_id)
);

create index parent_category
    on category (parent_category);

create table characteristic_template
(
    characteristic_template_id bigint auto_increment
        primary key,
    name                       varchar(50) not null
);

create table media
(
    media_id     bigint auto_increment
        primary key,
    path         varchar(50) null,
    name         varchar(50) not null,
    `index`      bigint      not null,
    media_object bigint      not null
);

create table product_template
(
    product_template_id bigint auto_increment
        primary key,
    category            bigint not null,
    constraint product_template_ibfk_1
        foreign key (category) references category (category_id)
);

create table product
(
    product_id       bigint auto_increment
        primary key,
    name             varchar(50) not null,
    shortDescription text        null,
    fullDescription  text        null,
    status           varchar(50) not null,
    product_template bigint      not null,
    constraint product_ibfk_1
        foreign key (product_template) references product_template (product_template_id)
);

create table characteristic
(
    characteristic_id bigint auto_increment
        primary key,
    name              varchar(50) not null,
    value             varchar(50) not null,
    product           bigint      not null,
    template          bigint      not null,
    constraint characteristic_ibfk_1
        foreign key (product) references product (product_id),
    constraint characteristic_ibfk_2
        foreign key (template) references characteristic_template (characteristic_template_id)
);

create index product
    on characteristic (product);

create index template
    on characteristic (template);

create index product_template
    on product (product_template);

create index category
    on product_template (category);

create table product_template_characteristic_template
(
    product_template        bigint not null,
    characteristic_template bigint not null,
    constraint product_template_characteristic_template_ibfk_1
        foreign key (product_template) references product_template (product_template_id),
    constraint product_template_characteristic_template_ibfk_2
        foreign key (characteristic_template) references characteristic_template (characteristic_template_id)
);

create index characteristic_template
    on product_template_characteristic_template (characteristic_template);

create index product_template
    on product_template_characteristic_template (product_template);

create table subscription
(
    subscription_id     bigint auto_increment
        primary key,
    name_subscription   varchar(50) not null,
    subscription_type   varchar(50) not null,
    subscription_object bigint      not null
);

create table user_account
(
    user_account_id    bigint auto_increment
        primary key,
    name               varchar(50)                        not null,
    surname            varchar(50)                        not null,
    patronymic         varchar(50)                        null,
    login              varchar(50)                        not null,
    password           varchar(50)                        not null,
    registration_date  datetime default CURRENT_TIMESTAMP not null,
    last_activity_date datetime                           not null,
    role               varchar(50)                        not null,
    constraint login
        unique (login)
);

create table claim
(
    claim_id      bigint auto_increment
        primary key,
    claim_type    varchar(50)                        not null,
    author        bigint                             not null,
    content       text                               not null,
    creation_date datetime default CURRENT_TIMESTAMP not null,
    close_date    datetime                           null,
    claim_object  bigint                             not null,
    constraint claim_ibfk_1
        foreign key (author) references user_account (user_account_id)
);

create index author
    on claim (author);

create table commentary
(
    commentary_id   bigint auto_increment
        primary key,
    commentary_type varchar(50)                          not null,
    author          bigint                               not null,
    content         text                                 not null,
    delete_state    tinyint(1) default 0                 null,
    creation_date   datetime   default CURRENT_TIMESTAMP not null,
    product         bigint                               not null,
    root_commentary bigint                               null,
    rating          int                                  null,
    constraint commentary_ibfk_1
        foreign key (author) references user_account (user_account_id),
    constraint commentary_ibfk_2
        foreign key (root_commentary) references commentary (commentary_id),
    constraint commentary_ibfk_3
        foreign key (product) references product (product_id)
);

create index author
    on commentary (author);

create index product
    on commentary (product);

create index root_commentary
    on commentary (root_commentary);

create table communication
(
    communication_id bigint auto_increment
        primary key,
    email            varchar(50) null,
    phone_number     varchar(50) null,
    user_account     bigint      not null,
    constraint communication_ibfk_1
        foreign key (user_account) references user_account (user_account_id)
);

create index user_account
    on communication (user_account);

create table reaction
(
    reaction_id   bigint auto_increment
        primary key,
    author        bigint                             not null,
    creation_date datetime default CURRENT_TIMESTAMP not null,
    commentary    bigint                             not null,
    type          varchar(50)                        not null,
    constraint reaction_ibfk_1
        foreign key (author) references user_account (user_account_id),
    constraint reaction_ibfk_2
        foreign key (commentary) references commentary (commentary_id)
);

create index author
    on reaction (author);

create index commentary
    on reaction (commentary);

create table shop
(
    shop_id bigint auto_increment
        primary key,
    name    varchar(50) not null,
    owner   bigint      not null,
    constraint shop_ibfk_1
        foreign key (owner) references user_account (user_account_id)
);

create table `order`
(
    order_id         bigint auto_increment
        primary key,
    customer         bigint                             not null,
    seller           bigint                             not null,
    last_change_date datetime default CURRENT_TIMESTAMP not null,
    state            varchar(50)                        not null,
    constraint order_ibfk_1
        foreign key (customer) references user_account (user_account_id),
    constraint order_shop_user_account_id_fk
        foreign key (seller) references shop (shop_id)
);

create table dialog
(
    dialog_id bigint auto_increment
        primary key,
    name      varchar(50) not null,
    order_id  bigint      not null,
    constraint dialog_ibfk_1
        foreign key (order_id) references `order` (order_id)
);

create index order_id
    on dialog (order_id);

create table message
(
    message_id bigint auto_increment
        primary key,
    author     bigint                             not null,
    content    text                               null,
    sent_date  datetime default CURRENT_TIMESTAMP not null,
    read_date  datetime                           null,
    dialog     bigint                             not null,
    constraint message_ibfk_1
        foreign key (author) references user_account (user_account_id),
    constraint message_ibfk_2
        foreign key (dialog) references dialog (dialog_id)
);

create index author
    on message (author);

create index dialog
    on message (dialog);

create index message
    on message (message_id);

create index customer
    on `order` (customer);

create index seller
    on `order` (seller);

create table order_product
(
    order_id   bigint not null,
    product_id bigint not null,
    quantity   int    not null,
    constraint order_product_ibfk_1
        foreign key (order_id) references `order` (order_id),
    constraint order_product_ibfk_2
        foreign key (product_id) references product (product_id)
);

create index order_id
    on order_product (order_id);

create index product_id
    on order_product (product_id);

create table promotion
(
    promotion_id   bigint auto_increment
        primary key,
    name           varchar(50)                        not null,
    discount       decimal                            null,
    unit           varchar(50)                        null,
    start_date     datetime default CURRENT_TIMESTAMP not null,
    end_date       datetime                           not null,
    shop           bigint                             not null,
    promotion_type varchar(50)                        not null,
    constraint promotion_ibfk_1
        foreign key (shop) references shop (shop_id)
);

create table price
(
    price_id   bigint auto_increment
        primary key,
    price_type varchar(50)                        not null,
    unit       varchar(20)                        not null,
    value      decimal                            not null,
    start_date datetime default CURRENT_TIMESTAMP not null,
    end_date   datetime                           null,
    product    bigint                             not null,
    promotion  bigint                             null,
    constraint price_ibfk_1
        foreign key (product) references product (product_id),
    constraint price_ibfk_2
        foreign key (promotion) references promotion (promotion_id)
);

create index product
    on price (product);

create index promotion
    on price (promotion);

create index shop
    on promotion (shop);

create table seller_shop
(
    seller_id bigint not null,
    shop_id   bigint not null,
    constraint seller_shop_ibfk_1
        foreign key (seller_id) references user_account (user_account_id),
    constraint seller_shop_ibfk_2
        foreign key (shop_id) references shop (shop_id)
);

create index seller_id
    on seller_shop (seller_id);

create index shop_id
    on seller_shop (shop_id);

create index owner
    on shop (owner);

create table subscription_subscribers
(
    subscription_id bigint not null,
    subscriber_id   bigint not null,
    constraint subscription_subscribers_ibfk_1
        foreign key (subscription_id) references subscription (subscription_id),
    constraint subscription_subscribers_ibfk_2
        foreign key (subscriber_id) references user_account (user_account_id)
);

create index subscriber_id
    on subscription_subscribers (subscriber_id);

create index subscription_id
    on subscription_subscribers (subscription_id);

create table warehouse
(
    warehouse_id bigint auto_increment
        primary key,
    city         varchar(50) not null,
    street       varchar(50) not null,
    number       varchar(50) not null,
    owner        bigint      not null,
    constraint warehouse_ibfk_1
        foreign key (owner) references shop (shop_id)
);

create index owner
    on warehouse (owner);

create table warehouse_product
(
    warehouse_id bigint not null,
    product_id   bigint not null,
    constraint warehouse_product_ibfk_1
        foreign key (warehouse_id) references warehouse (warehouse_id),
    constraint warehouse_product_ibfk_2
        foreign key (product_id) references product (product_id)
);

create index product_id
    on warehouse_product (product_id);

create index warehouse_id
    on warehouse_product (warehouse_id);


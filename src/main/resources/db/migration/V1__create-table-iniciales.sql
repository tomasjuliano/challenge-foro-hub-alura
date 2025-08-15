create table usuarios(

    id bigint not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null unique,
    contrasena varchar(255) not null,

    primary key (id)
);

create table cursos(

    id bigint not null auto_increment,
    nombre varchar(255) not null unique,
    categoria varchar(255) not null,

    primary key (id)

);

create table topicos(

    id bigint not null auto_increment,
    titulo varchar(255) not null unique,
    mensaje text not null,
    fecha_creacion datetime not null,
    estado varchar(100) not null,
    autor_id bigint not null,
    curso_id bigint not null,

    primary key (id),
    foreign key (autor_id) references usuarios(id),
    foreign key (curso_id) references cursos(id)

);

create table respuestas(

    id bigint not null auto_increment,
    mensaje text not null,
    fecha_creacion datetime not null,
    topico_id bigint not null,
    autor_id bigint not null,
    solucion boolean not null default false,

    primary key (id),
    constraint fk_respuesta_topico foreign key (topico_id) references topicos(id),
    constraint fk_respuesta_autor foreign key (autor_id) references usuarios(id)
);
create database if not exists Postif;
use Postif;
create table user(
	id int auto_increment primary key,
    nome varchar(55),
    email varchar(55),
    nascimento varchar(11),
    username varchar (15) unique,
    senha varchar(20) not null
    );
    
create table tarefas (
	id INT auto_increment primary key,
    nome varchar(20) not null,
    descricao varchar(100),
    dataIn date,
    dataOut date,
    userId int not null,
	foreign key(userId) references user(id)
);
    
create table posts(
	id int auto_increment primary key,
    nome varchar(20) not null,
    tarefaId int,
    descricao varchar(50),
    userId int,
    dataPostagem date default (current_date),
    nomeAutor varchar(15) unique,

    foreign key(tarefaId) references tarefas(id),
    foreign key(userId) references user(id)
    );
    
create table comentarios(
	id int auto_increment primary key,
	comentario varchar(150) not null,
    userId int not null,
    postId int not null,
    foreign key(userId) references user(id) on delete cascade,
    foreign key(postId) references posts(id) on delete cascade
);

create table post_associacao(
	subPostId int not null,
    postId int not null,
    
    primary key(subPostId, postId),
    foreign key(postId) references posts(id) on delete cascade,
    foreign key(subPostId) references posts(id) on delete cascade
);
    
    

    
    
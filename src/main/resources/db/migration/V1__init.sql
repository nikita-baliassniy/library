BEGIN;

DROP SCHEMA IF EXISTS hiber;
CREATE SCHEMA hiber;
SET search_path TO hiber,public;
DROP TABLE IF EXISTS books CASCADE;

CREATE TABLE books (
    id                  BIGSERIAL PRIMARY KEY,
--     info_id             BIGSERIAL NOT NULL REFERENCES books_info (id),
--     storage_id          BIGSERIAL NOT NULL REFERENCES books_storage (id),
    title               VARCHAR(255) NOT NULL,
    price               NUMERIC(8, 2),
    description         TEXT,
    year_of_publish     INT,
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);

CREATE TABLE books_info (
    id                  BIGSERIAL PRIMARY KEY,
    book_id             BIGINT NOT NULL REFERENCES books (id),
    size                INT NOT NULL,
    score               NUMERIC(2, 1),
    age_recommendation  INT
);

CREATE TABLE books_storage (
    id                  BIGSERIAL PRIMARY KEY,
    book_id             BIGINT NOT NULL REFERENCES books (id),
    link_cover          VARCHAR(255),
    link_fb2            VARCHAR(255),
    link_pdf            VARCHAR(255),
    link_doc            VARCHAR(255),
    link_audio          VARCHAR(255)
);

CREATE TABLE authors (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    date_of_birth       DATE,
    biography           TEXT,
    country             VARCHAR(50)
);

CREATE TABLE books_authors (
    book_id             BIGINT NOT NULL REFERENCES books (id),
    author_id           BIGINT NOT NULL REFERENCES authors (id),
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE genres (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(255) NOT NULL
);

CREATE TABLE books_genres (
    book_id             BIGINT NOT NULL REFERENCES books (id),
    genre_id            BIGINT NOT NULL REFERENCES genres (id),
    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE users (
    id                  BIGSERIAL PRIMARY KEY,
    username            VARCHAR(255) NOT NULL,
    email               VARCHAR(50) NOT NULL UNIQUE,
    password            VARCHAR(80) NOT NULL,
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);

CREATE TABLE users_info (
    id                  BIGSERIAL PRIMARY KEY,
    user_id             BIGINT NOT NULL REFERENCES users (id),
    name                VARCHAR(255),
    phone               VARCHAR(15),
    discount            INT,
    address             VARCHAR(255),
    date_of_birth       DATE
);

CREATE TABLE roles (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(50) NOT NULL UNIQUE,
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);

CREATE TABLE users_roles (
    user_id             BIGINT NOT NULL REFERENCES users (id),
    role_id             BIGINT NOT NULL REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE comments (
    id                  BIGSERIAL PRIMARY KEY,
    book_id             BIGINT NOT NULL REFERENCES books (id),
    text                VARCHAR(2000),
    user_id             BIGINT NOT NULL REFERENCES users (id),
    score               NUMERIC(1, 0),
    created_at          TIMESTAMP default current_timestamp
);

CREATE TABLE orders (
    id                  BIGSERIAL PRIMARY KEY,
    owner_id            BIGINT NOT NULL REFERENCES users (id),
    total_price         NUMERIC(8, 2),
    address             VARCHAR(255),
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);

CREATE TABLE order_items (
    id                  BIGSERIAL PRIMARY KEY,
    order_id            BIGINT REFERENCES orders (id),
    book_id             BIGINT REFERENCES books (id),
    title               VARCHAR(255),
    quantity            INT,
    price_per_book      NUMERIC(8, 2),
    price               NUMERIC(8, 2),
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);

create table carts (
    id                  UUID PRIMARY KEY,
    price               NUMERIC(8, 2)
);

create table cart_items (
    id                  BIGSERIAL PRIMARY KEY,
    cart_id             UUID REFERENCES carts (id),
    book_id             BIGINT REFERENCES books (id),
    title               VARCHAR(255),
    quantity            INT,
    price_per_book      NUMERIC(8, 2),
    price               NUMERIC(8, 2),
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);

INSERT INTO users (username, email, password) VALUES
('admin','123@asd.ru', '111'),
('admin_mini','444@asd.ru', '222');

INSERT INTO users_info (user_id, name, phone, discount, address, date_of_birth) VALUES
(1, 'John', '89991234567', 10, 'Москва, ул. не знаю', PARSEDATETIME('10.05.1981', 'dd.MM.yyyy')),
(2, 'Vova', '89992223344', 20, 'Москва, Кремль', PARSEDATETIME('07.10.1952', 'dd.MM.yyyy'));

INSERT INTO books (title, price, description, year_of_publish)
VALUES
('Приключения Тома Сойера', 29.90,
'Вышедшая в 1876 году повесть Марка Твена о приключениях мальчика, живущего в небольшом американском городке Сент-Питерсберг (Санкт-Петербург) в штате Миссури. Действие в книге происходит до событий Гражданской войны в США, при этом ряд моментов в этой книге и её продолжении, «Приключениях Гекльберри Финна», а также обстоятельства жизни автора, во многом легшие в основу книг, уверенно указывают на первую половину 1840-х годов.',
 2018),
('Гарри Поттер и философский камень', 24.90, 'Первый роман в серии книг про юного волшебника Гарри Поттера, написанный Дж. К. Роулинг. В нём рассказывается, как Гарри узнает, что он волшебник, встречает близких друзей и немало врагов в Школе Чародейства и Волшебства «Хогвартс», а также с помощью своих друзей пресекает попытку возвращения злого волшебника Волан-де-Морта, который убил родителей Гарри',
 2020),
('Приключения Шерлока Холмса', 22.90, 'Сборник из 12 детективных рассказов, созданных Артуром Конаном Дойлом.', 2015),
('Портрет Дориана Грея', 32.90, 'Единственный роман Оскара Уайльда. В жанровом отношении представляет смесь романа воспитания с моральной притчей; оказался самым успешным произведением Уайльда, экранизировался в разных странах мира более 30 раз. Существует в трёх версиях — журнальный вариант в тринадцати главах, опубликованный в Lippincott’s Monthly Magazine в июле 1890 года, подвергшийся сокращениям, сделанными редактором журнала; первое книжное издание в двадцати главах (апрель 1891 года), в которое автором были добавлены главы III, V, XV—XVIII, а последняя глава разделена на две, ставшие главами XIX и XX и «бесцензурная» версия романа в тринадцати главах, представляющая собой первоначальную оригинальную машинопись Уайльда, впервые опубликованную в 2011 году Издательством Гарвардского университета. В основе всех изданий на английском языке и переводов лежит книжная версия 1891 года.',
 2012);

 INSERT INTO books_info (book_id, size, score, age_recommendation)
VALUES
(1, 240, 4.6, 6),
(2, 260, 3.7, 6),
(3, 710, 4.6, 16),
(4, 270, 4.6, 16);

INSERT INTO books_storage (book_id, link_cover, link_fb2, link_pdf, link_doc, link_audio)
VALUES
(1, '/storage/cover/test1.txt', '/storage/fb2/test1.fb2', '/storage/pdf/test1.pdf', '/storage/doc/test1.doc', '/storage/audio/test1.mp3'),
(2, '/storage/cover/test2.txt', '/storage/fb2/test2.fb2', '/storage/pdf/test2.pdf', '/storage/doc/test2.doc', '/storage/audio/test2.mp3'),
(3, '/storage/cover/test3.txt', '/storage/fb2/test3.fb2', '/storage/pdf/test3.pdf', '/storage/doc/test3.doc', '/storage/audio/test3.mp3'),
(4, '/storage/cover/test4.txt', '/storage/fb2/test4.fb2', '/storage/pdf/test4.pdf', '/storage/doc/test4.doc', '/storage/audio/test4.mp3');

INSERT INTO comments (book_id, text, user_id, score) VALUES
(1, 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap', 1, 4),
(2, 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley', 2, 3),
(1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus euismod, ex in auctor convallis, velit enim maximus neque, a tincidunt leo mi eget turpis. Proin rutrum volutpat quam id laoreet. Aliquam ullamcorper ultrices malesuada. Duis laoreet, dolor ac feugiat mollis.', 2, 2),
(3, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse scelerisque ipsum id nisl maximus fringilla. Nunc sed nibh porttitor mi dignissim pulvinar at ut nulla.', 1, 5);

INSERT INTO authors (name, date_of_birth, biography, country)
VALUES
('Марк Твен', '1835-11-30', 'Американский писатель, юморист, журналист и общественный деятель. Его творчество охватывает множество жанров — юмор, сатиру, философскую фантастику, публицистику и другие, и во всех этих жанрах он неизменно занимает позицию гуманиста и демократа. Уильям Фолкнер писал, что Марк Твен был «первым по-настоящему американским писателем, и все мы с тех пор — его наследники», а Эрнест Хемингуэй считал, что вся современная американская литература вышла из одной книги Марка Твена, которая называется «Приключения Гекльберри Финна». Из русских писателей о Марке Твене особенно тепло отзывались Максим Горький и Александр Куприн.',
'США'),
('Джоан Роулинг', '1965-07-31', 'Британская писательница, сценаристка и кинопродюсер, наиболее известная как автор серии романов о Гарри Поттере. Книги о Гарри Поттере получили несколько наград и были проданы в количестве более 500 миллионов экземпляров. Они стали самой продаваемой серией книг в истории и основой для серии фильмов, ставшей третьей по кассовому сбору серией фильмов в истории. Джоан Роулинг сама утверждала сценарии фильмов, а также вошла в состав продюсеров последних двух частей.',
'Великобритания'),
('Артур Конан Дойл', '1930-07-07', 'Английский писатель (по образованию врач) ирландского происхождения, автор многочисленных приключенческих, исторических, публицистических, фантастических и юмористических произведений. Создатель классических персонажей детективной, научно-фантастической и историко-приключенческой литературы: гениального сыщика Шерлока Холмса, эксцентричного профессора Челленджера, бравого кавалерийского офицера Жерара, благородного рыцаря сэра Найджела. Со второй половины 1910-х годов и до конца жизни — активный сторонник и пропагандист идей спиритуализма.',
'Великобритания'),
('Оскар Уйальд', '1854-10-16', 'Ирландский писатель и поэт. Один из самых известных драматургов позднего Викторианского периода, одна из ключевых фигур эстетизма и европейского модернизма.',
'Ирландия');

INSERT INTO genres (name)
VALUES
('детектив'),
('криминал'),
('фэнтези'),
('научная фантастика'),
('приключения'),
('детская литература'),
('мистика'),
('литература 21 века'),
('литература 20 века'),
('литература 19 века');

INSERT INTO books_genres (book_id, genre_id)
VALUES
(1, 5),
(1, 6),
(1, 10),
(2, 5),
(2, 6),
(2, 9),
(2, 3),
(3, 1),
(3, 2),
(3, 5),
(3, 10),
(4, 10),
(4, 7);

INSERT INTO books_authors (book_id, author_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

COMMIT

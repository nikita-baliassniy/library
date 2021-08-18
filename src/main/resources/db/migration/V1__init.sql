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
    link_epub           VARCHAR(255),
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
--     username            VARCHAR(255) NOT NULL,
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

INSERT INTO users (/*username,*/ email, password) VALUES
(/*'admin',*/'123@asd.ru', '$2a$12$XwOoolcqL5gwjHAaqb4tTes9hoedw50kyOdq6I4xv2x4QHg9VtQ/e'), -- 111
(/*'admin_mini',*/'444@asd.ru', '$2a$12$aRjLVCKZHGpQ26dm7zZfjOwDXq1MUA/dEGHt67VbueAnLMBnwP5G.'); --222

INSERT INTO users_info (user_id, name, phone, discount, address, date_of_birth) VALUES
(1, 'John', '89991234567', 10, 'Москва, ул. не знаю', PARSEDATETIME('10.05.1981', 'dd.MM.yyyy')),
(2, 'Vova', '89992223344', 20, 'Москва, Кремль', PARSEDATETIME('07.10.1952', 'dd.MM.yyyy'));

INSERT INTO roles (name) VALUES
('ROLE_ADMIN'),
('ROLE_MANAGER'),
('ROLE_USER');

INSERT INTO users_roles (user_id, role_id) VALUES
(1, 1),
(2, 3);

INSERT INTO books (title, price, description, year_of_publish)
VALUES
('Приключения Тома Сойера', 29.90,
'Вышедшая в 1876 году повесть Марка Твена о приключениях мальчика, живущего в небольшом американском городке Сент-Питерсберг (Санкт-Петербург) в штате Миссури. Действие в книге происходит до событий Гражданской войны в США, при этом ряд моментов в этой книге и её продолжении, «Приключениях Гекльберри Финна», а также обстоятельства жизни автора, во многом легшие в основу книг, уверенно указывают на первую половину 1840-х годов.',
 2018),
('Гарри Поттер и философский камень', 24.90, 'Первый роман в серии книг про юного волшебника Гарри Поттера, написанный Дж. К. Роулинг. В нём рассказывается, как Гарри узнает, что он волшебник, встречает близких друзей и немало врагов в Школе Чародейства и Волшебства «Хогвартс», а также с помощью своих друзей пресекает попытку возвращения злого волшебника Волан-де-Морта, который убил родителей Гарри',
 2020),
('Приключения Шерлока Холмса', 22.90, 'Сборник из 12 детективных рассказов, созданных Артуром Конаном Дойлом.', 2015),
('Портрет Дориана Грея', 32.90, 'Единственный роман Оскара Уайльда. В жанровом отношении представляет смесь романа воспитания с моральной притчей; оказался самым успешным произведением Уайльда, экранизировался в разных странах мира более 30 раз. Существует в трёх версиях — журнальный вариант в тринадцати главах, опубликованный в Lippincott’s Monthly Magazine в июле 1890 года, подвергшийся сокращениям, сделанными редактором журнала; первое книжное издание в двадцати главах (апрель 1891 года), в которое автором были добавлены главы III, V, XV—XVIII, а последняя глава разделена на две, ставшие главами XIX и XX и «бесцензурная» версия романа в тринадцати главах, представляющая собой первоначальную оригинальную машинопись Уайльда, впервые опубликованную в 2011 году Издательством Гарвардского университета. В основе всех изданий на английском языке и переводов лежит книжная версия 1891 года.',
 2012),
('Последнее желание', 250.00, 'Книгой «Последнее желание» начинается один из самых популярных циклов жанра фэнтези. В нее включены семь историй о начале пути ведьмака Геральта из Ривии, его друзьях и возлюбленной, нелегкой судьбе, работе по уничтожению нечисти и врагов мира, волшебном мире, населенном колдунами, эльфами, чародейками, гномами, оборотнями, драконами и, конечно же, людьми с их пороками и добродетелями. Начав читать произведение, вы познакомитесь с удивительным охотником за нечистью, прошедшим через невероятные испытания и пережившим ужасные потери. Геральт – это воин, ведущий непрерывную борьбу с силами зла. А рядом с ним всегда находится его верный трубадур Лютик, слагающий баллады о приключениях, подвигах и прекрасных дамах…',
2016),
('Меч предназначения', 300.00, 'Ведьмак – это мастер меча и мэтр волшебства, ведущий непрерывную войну с кровожадными монстрами, которые угрожают покою сказочной страны. «Ведьмак» – это мир на острие меча, ошеломляющее действие, незабываемые ситуации, великолепные боевые сцены. Читайте продолжение саги о Ведьмаке!',
2009),
('Кровь Эльфов', 340.00, 'Цинтра захвачено Нильфгаардской империей. Всюду пламя и разрушения, сотни погибших. Прекрасное королевство пало.
Наследнице Цири чудом удается спастись. Напуганную, потерявшую близких и дом девочку Геральт доставляет в убежище ведьмаков. Неожиданно для всех у принцессы открываются магические способности.
Чтобы понять их природу, Геральт обращается за помощью к чародейке. Однако она советует ведьмаку призвать свою бывшую возлюбленную Йеннифэр. Ибо только она сможет научить девочку пользоваться ее даром…',
2009),
('Час презрения', 330.00, 'Йеннифер и княжна Цирилла отправились на остров Танедд, чтобы принять участие в Чародейском Сборе. Чародейка не только хочет обсудить судьбы мира, но и устроить Цири в школу Аретузе. Геральт сопровождает их на всем пути странствия, распространяя слух о гибели Цири во время нападения на Цинтру.
Все планы рушит мятеж чародеев, послуживший началу новой войны. Геральту чудом удается спастись, в Цири перемещается в незнакомое место…
Удастся ли главным героям выжить в новом мире и найти друг друга? Чья сторона одержит победу в войне между чародеями и людьми? Об этом читайте в новой части цикла «Ведьмак».',
2009),
('Крещение огнем', 340.00, 'Нильфгаард продолжает наступление на северные королевства.
Тем временем Геральт, восстановивший силы у дриад после восстания на Таннеде, отправляется на поиски Цири. В этом нелегком путешествии к ведьмаку присоединяются его старые знакомцы: трубадур Лютик, лучница Мария Барринг, рыцарь Кагыр и вампир Эмиль Регис. На спасение Цири отправляется и Йеннифер, которая смогла сбежать из плена.
Сама Цири под именем Фалька стала разбойничать в банде под названием Крысы. Однако за бандой уже следит безжалостный охотник за головами Лео Бонарт…',
2014),
('Башня ласточки', 245.00, 'Все окончательно перепуталось, интриги сплелись в тугой клубок, распутать который никому не по силам. Идет война, гибнут люди и нелюди. Избранные чародеи решили, что они в праве вершить судьбы мира. Йенифер попала в серьезную переделку, и вряд ли ее спасет даже ее хладнокровие и цинизм. Не лучше дела обстоят у Геральда и Цири.
К чему приведет этот смертоносный круговорот событий?',
2014),
('Владычица озера', 290.00, 'Книга «Владычица Озера» – это потрясающее завершение одной из самых популярных фэнтези-саг современности. Читателю откроются тайны прошлого и настоящего, а главные герои смогут обрести свое счастье.
Цири найдет Башню Ласточки, которую хочет в своих целях использовать Народ Ольх. При попытке бегства Цири потеряется в параллельных мирах…
Объединенные Королевства Севера объединят силы и дадут бой Нильфгаарду.
Геральт со своими друзьями выйдет в сражение против Вильгефорца. Эта схватка обернется невосполнимыми потерями сил добра…',
2014),
('Азазель', 300.00, 'Погожим майским днем 1876 года в Александровском саду происходит неслыханное. На глазах у изумленной публики симпатичный молодой человек пускает себе пулю в висок. Вскоре выясняется: погибший – некто Петр Кокорин, студент юридического факультета. В предсмертной записке Кокорин завещает все свое немалое имущество баронессе Эстер, подданной Британии, которая занимается образованием и воспитанием сирот.
Все это кажется очень странным чиновнику четырнадцатого класса Эрасту Петровичу Фандорину. С одобрения своего начальника, пристава Сыскного управления Ксаверия Грушина, Фандорин берется за расследование гибели студента. Вскоре он выясняет, что в тот день свести счеты с жизнью тем же способом пытался еще один человек. Что или кто связывает этих двоих? И главное, кто заставляет молодых людей играть в зловещую рулетку со смертью?..',
2010),
('Турецкий гамбит', 290.00, '1877 год, Российская империя участвует в жесточайшей русско-турецкой войне. Юная девушка Варвара Суворова, петербургская красавица передовых взглядов и почти нигилистка, отправляется в зону боевых действий к жениху. Началось путешествие как веселое приключение, а затем Варвара вдруг остается одна в сомнительной придорожной корчме, и случайные попутчики даже играют на нее в карты…
О приключениях Варвары Суворовой на Балканах, оказавшейся в самом центре интригующих событий и вместе с тайным агентом Российской империи Эрастом Фандориным разоблачившей обширный шпионский заговор, читайте в романе «Турецкий гамбит».',
2010),
('Левиафан', 400.00, '15 марта 1878 года на рю де Гренель в Париже совершено страшное убийство. Убит лорд Литтлби и девять его слуг. Преступник не взял из дома ничего, кроме статуэтки бога Шивы и цветного платка. Расследование приводит комиссара полиции Гоша на роскошный корабль «Левиафан», плывущий в Калькутту. Убийца на корабле, но кто это? Среди подозреваемых, каждый из которых прячет свою тайну, английский аристократ, офицер Японской армии, беременная жена швейцарского банковского служащего и молодой русский дипломат с седыми висками…',
2010),
('Смерть Ахиллеса', 340.00, 'Роман Бориса Акунина «Смерть Ахиллеса» – это добротный детектив, приятный для не обременяющего мозг чтения и не раздражающий, в отличие от большинства его современных собратьев, глупостью или пошлостью.
В этой книге описывается расследование обстоятельств смерти всенародного любимца, генерала Соболева, сыщиком Эрастом Фандориным, а также приводится развернутая история превращения мальчика Ахимаса в закоренелого негодяя…',
2010),
('Особые поручения', 320.00, 'В Москве орудует шайка мошенников «Пиковый Валет». Они нахальны, изобретательны и уверены в своей безнаказанности. Они проворачивают чрезвычайно дерзкие аферы и бесследно исчезают с места преступления. Но за дело берется разоблачитель заговоров, мастер по тайным расследованиям, кавалер Орденов Хризантем, специалист по ведению деликатных и тайных дел Эраст Петрович Фандорин.',
2010),
('Статский советник', 300.00, '1891 год. Брожение в умах, революционные идеи популярны среди молодежи, повсюду возникают революционные кружки. Но не для всех это только мода.
Группа, называющая себя «Б. Г.», работает точно и дерзко. Убит сибирский генерал-губернатор, убийца – человек, предъявивший документы на имя Эраста Фандорина. Эраст Петрович принимает вызов и берется за расследование. Кто стоит за буквами «Б. Г.»? Что сделало их террористами? Ради чего они совершают свои кровавые преступления?',
2010),
('Коронация, или Последний из романов', 310.00, 'Действие этого романа происходит в 1896 году, накануне коронации императора Николая II. Похищен Михаил, четырёхлетний сын великого князя Георгия Александровича. Похититель, называющий себя «доктор Линд», требует бриллиант «граф Орлов», которым украшен императорский скипетр, в обмен на принца. Если сделка не состоится, ребёнок будет возвращён родителям по частям. Но без скипетра не может состояться коронация. Эраст Петрович Фандорин берётся спасти честь монархии.',
2010),
('Любовник смерти', 300.00, 'Жила себе красавица с отцом и матерью. Вот и возраст подошел, сосватали ее. Но не суждено было счастью долго продлиться. В день свадьбы умер жених. Похоронили, убивалась невеста сильно. Погоревала, опять замуж собралась. Но перед венчанием и второй жених скончался. С кем бы ни жила девушка после, все умирали. Вот прозвали ее не иначе, как Смерть. Но недостатка в ухажерах она не испытывала. Вот и теперь Князь приударил, любовником Смерти стал. Долго ли ему землю топтать осталось?',
2010),
('Алмазная колесница', 400.00, '«Алмазная колесница» издана двухтомником, причем оба тома помещаются под одной обложкой.
В первой книге «Ловец стрекоз» читатель следит за двумя героями – Хорошим (железнодорожный чиновник Фандорин) и Плохим (японский супершпион Рыбников). Действуют они втайне друг от друга на фоне русско-японской войны 1905 года и предреволюционной смуты. Плохой желает взорвать железную дорогу и устроить мятеж в первопрестольной, Хороший – помешать злодейским планам.
Вторая книга «Между строк» повествует о приключениях Фандорина в Японии 1878 года, его невооруженных конфликтах с самураями и ниндзя, любви к куртизанке и прослушивании курса вербовочных лекций на тему «Убийцы и воры – буддоизбранные счастливчики».',
2010),
('Ангелы и демоны', 360.00, 'Контейнер с опасной антиматерией похищен. И за этим преступлением, по мнению профессора Роберта Лэнгдона, стоит тайный оккультный орден иллюминатов, восставший из небытия.
Путем долгих вычислений Лэнгдону удается узнать, что штаб-квартира ордена находится в Ватикане. Теперь ему предстоит как можно скорее приехать в Рим и заняться поисками контейнера, чтобы спасти мир. Но для этого нужно будет разгадать множество сложных загадок. К счастью, профессору Гарвардского университета это под силу.',
2013),
('Код да Винчи', 300.00, 'Страшное преступление совершается в стенах Лувра: куратор Жан Соньер жестоко убит, на его обнаженном теле начертаны странные знаки, а рядом с его трупом виднеются пурпурные буквы и цифры. В качестве помощника в расследовании полиция приглашает профессора Гарвардского университета по истории культуры и религии Роберта Лэнгдона.
Как вскоре узнает Лэнгдон, ключ к раскрытию тайны убийства лежит в картинах Леонардо да Винчи, и только ему под силу разгадать эти сложнейшие загадки. Вскоре профессор понимает, что дело об убийстве Соньера куда более запутанное, чем могло показаться на первый взгляд.',
2013),
('Происхождение', 450.00, '«Происхождение» – пятая книга американского писателя Дэна Брауна о гарвардском профессоре, специалисте по религиозной символике Роберте Лэнгдоне. В этот раз все начинается с, возможно, одного из наиболее знаковых событий в истории: наконец-то стало известно, откуда произошло человечество. Футуролог Эдмонд Кирш, совершивший невероятное открытие, был всего лишь в шаге от того, чтобы полностью изменить представление современников о мире. Однако его речи не суждено было прозвучать в стенах Музея Гуггенхайма. Ученого убили на глазах гостей. И начался хаос…
Лэнгдона снова ждут опасные приключения: от побега из музея под градом пуль до поиска зашифрованных ключей, позволяющих встретиться лицом к лицу с открытием Кирша.',
2017),
('Унесенные ветром', 500.0, 'Согласно легенде, создание романа «Унесенные ветром» началось с того, как Маргарет Митчелл написала главную фразу последней главы: «Ни одного из любимых ею мужчин Скарлетт так и не смогла понять и вот – потеряла обоих». Последующая работа над произведением продолжалась около десяти лет и потребовала от писательницы огромной самоотдачи и напряженного труда. Стремясь проникнуть в самый дух эпохи, Митчелл кропотливо изучала историю родной Атланты, использовала газеты и журналы середины XIX века. На страницах ее рукописи оживали рассказы очевидцев Гражданской войны и семейные предания. Некоторые сцены Митчелл переписывала по четыре-пять раз, а что касается первой главы, писательницу удовлетворил лишь 60-й вариант!
Роман, вышедший весной 1936 года, имел беспрецедентный успех и сразу побил все рекорды по популярности и тиражам во всей истории американской литературы. А одноименная экранизация с Вивьен Ли и Кларком Гейблом в главных ролях завоевала 10 премий «Оскар» и стала одной из самых знаменитых лент в истории мирового кинематографа.',
2010),
('До встречи с тобой', 290.00, 'Лу Кларк знает, сколько шагов от автобусной остановки до ее дома. Она знает, что ей очень нравится работа в кафе и что, скорее всего, она не любит своего бойфренда Патрика. Но Лу не знает, что вот-вот потеряет свою работу и что в ближайшем будущем ей понадобятся все силы, чтобы преодолеть свалившиеся на нее проблемы.
Уилл Трейнор знает, что сбивший его мотоциклист отнял у него желание жить. И он точно знает, что надо сделать, чтобы положить конец всему этому. Но он не знает, что Лу скоро ворвется в его мир буйством красок. И они оба не знают, что навсегда изменят жизнь друг друга.
В первые месяцы после выхода в свет романа Джоджо Мойес «До встречи с тобой» было продано свыше полумиллиона экземпляров. Книга вошла в список бестселлеров «Нью-Йорк таймс», переведена на 31 язык. Права на ее экранизацию купила киностудия «Метро-Голдвин-Майер».',
2013),
('Селфи с судьбой', 280.00, 'В магазинчике «Народный промысел» в селе Сокольничьем найдена задушенной богатая дама. Она частенько наведывалась в село, щедро жертвовала на восстановление колокольни и пользовалась уважением. Преступник – шатавшийся поблизости пьянчужка – задержан по горячим следам… Профессор Илья Субботин приезжает в село, чтобы установить истину. У преподавателя физики странное хобби – он разгадывает преступления. На него вся надежда, ибо копать глубже никто не станет, дело закрыто. В Сокольничьем вокруг Ильи собирается странная компания: поэтесса с дредами; печальная красотка в мехах; развеселая парочка, занятая выкладыванием селфи в Интернет; экскурсоводша; явно что-то скрывающий чудаковатый парень; да еще лощеного вида джентльмен.
Кто-то из них убил почтенную даму. Но кто? И зачем?..',
2017),
('С небес на землю', 330.00, 'Он ведет странную жизнь и, кажется, не слишком ею доволен. У него странная профессия, странные привычки, даже имя странное – Алекс Шан-Гирей!..
Издательство, в которое Алекса пригласили на работу, на первый взгляд кажется вполне мирным, уютным и процветающим. Все друг друга любят и заняты благородным делом – изданием книг.
Все пойдет прахом как раз в тот день, когда в коридоре издательства обнаружится труп. Кто этот человек? Как он туда попал? Выходит, убил его один из тех самых милых и интеллигентных людей, занятых благородным делом?! И как докопаться до истины?!
А докапываться придется, потому что Алексу тоже угрожает смертельная опасность – он увяз в давней тяжелой ненависти, совсем позабыл про любовь, потерялся по дороге. Да и враг, самый настоящий, реальный, хитрый и сильный, не дремлет!..
Ему во всем придется разбираться – в ненависти, в любви, во врагах и друзьях, ибо он не знает, кто друг, а кто враг! Ему придется вернуться с небес на землю, оглядеться по сторонам, перевести дыхание и понять, что здесь, на земле, все не так уж и плохо!..',
2011),
('Пес по имени Мани', 200.00, 'Трогательная история говорящего Лабрадора Мани, который оказался настоящим финансовым гением, в доступной форме открывает перед детьми и взрослыми секреты денег.
Для широкого круга читателей.',
2017),
('Пора зрабатывать больше!', 240.00, 'Эта книга – пособие для достижения финансового благополучия. Ее написал автор нашумевшего бестселлера «Путь к финансовой свободе» Бодо Шефер. Его советы помогут всем, кто желает добиться материальной независимости и взлететь по карьерной лестнице. Применить рекомендации на практике смогут и безработный, и владелец бизнеса, и работники по найму.
Автор делится простыми и эффективными способами заработка, рассказывает о подводных камнях на пути к богатству, приводит множество секретов, которые помогут полюбить свою работу.',
2018),
('Ходячий замок', 390.00, 'Книги английской писательницы Дианы У. Джонс настолько ярки, что так и просятся на экран. По ее бестселлеру «Ходячий замок» знаменитый мультипликатор Хаяо Миядзаки («Унесенные призраками»), обладатель «Золотого льва» – высшей награды Венецианского фестиваля, снял анимационный фильм, побивший в Японии рекорд кассовых сборов.
…Софи живет в сказочной стране, где ведьмы и русалки, семимильные сапоги и говорящие собаки – обычное дело. Поэтому, когда на нее обрушивается ужасное проклятие коварной Болотной Ведьмы, Софи ничего не остается, как обратиться за помощью к таинственному чародею Хоулу, обитающему в Ходячем замке. Однако, чтобы освободиться от чар, Софи предстоит разгадать немало загадок и прожить в замке у Хоула гораздо дольше, чем она рассчитывала. А для этого нужно подружиться с огненным демоном, поймать падающую звезду, подслушать пение русалок, отыскать мандрагору и многое, многое другое.',
2009);

INSERT INTO books_info (book_id, size, score, age_recommendation)
VALUES
(1, 240, 4.6, 6),
(2, 260, 3.7, 6),
(3, 710, 4.6, 16),
(4, 270, 4.6, 16),
(5, 310, 4.8, 16),
(6, 380, 4.9, 16),
(7, 330, 4.8, 16),
(8, 360, 4.8, 16),
(9, 380, 4.9, 16),
(10, 490, 4.8, 16),
(11, 590, 4.7, 16),
(12, 230, 4.7, 16),
(13, 210, 4.8, 16),
(14, 250, 4.7, 16),
(15, 340, 4.8, 16),
(16, 160, 4.6, 16),
(17, 310, 4.8, 16),
(18, 380, 4.7, 16),
(19, 300, 4.8, 16),
(20, 690, 4.8, 16),
(21, 670, 4.7, 18),
(22, 560, 4.7, 16),
(23, 530, 4.4, 16),
(24, 780, 4.9, 18),
(25, 410, 4.8, 16),
(26, 280, 4.5, 16),
(27, 290, 4.6, 16),
(28, 170, 4.8, 12),
(29, 310, 4.0, 16),
(30, 280, 4.8, 12);

INSERT INTO books_storage (book_id, link_cover, link_fb2, link_pdf, link_epub, link_audio)
VALUES
(1, '/storage/cover/1.jpg', '/storage/fb2/1.fb2', '/storage/pdf/1.pdf', '/storage/epub/1.epub', '/storage/audio/1.mp3'),
(2, '/storage/cover/2.jpg', '/storage/fb2/2.fb2', '/storage/pdf/2.pdf', '/storage/epub/2.epub', '/storage/audio/2.mp3'),
(3, '/storage/cover/3.jpg', '/storage/fb2/3.fb2', '/storage/pdf/3.pdf', '/storage/epub/3.epub', '/storage/audio/3.mp3'),
(4, '/storage/cover/4.jpg', '/storage/fb2/4.fb2', '/storage/pdf/4.pdf', '/storage/epub/4.epub', '/storage/audio/4.mp3'),
(5, '/storage/cover/5.jpg', '/storage/fb2/5.fb2', '/storage/pdf/5.pdf', '/storage/epub/5.epub', '/storage/audio/3.mp3'),
(6, '/storage/cover/6.jpg', '/storage/fb2/6.fb2', '/storage/pdf/6.pdf', '/storage/epub/6.epub', '/storage/audio/3.mp3'),
(7, '/storage/cover/7.jpg', '/storage/fb2/7.fb2', '/storage/pdf/7.pdf', '/storage/epub/7.epub', '/storage/audio/3.mp3'),
(8, '/storage/cover/8.jpg', '/storage/fb2/8.fb2', '/storage/pdf/8.pdf', '/storage/epub/8.epub', '/storage/audio/3.mp3'),
(9, '/storage/cover/9.jpg', '/storage/fb2/9.fb2', '/storage/pdf/9.pdf', '/storage/epub/9.epub', '/storage/audio/3.mp3'),
(10, '/storage/cover/10.jpg', '/storage/fb2/10.fb2', '/storage/pdf/10.pdf', '/storage/epub/10.epub', '/storage/audio/3.mp3'),
(11, '/storage/cover/11.jpg', '/storage/fb2/11.fb2', '/storage/pdf/11.pdf', '/storage/epub/11.epub', '/storage/audio/3.mp3'),
(12, '/storage/cover/12.jpg', '/storage/fb2/12.fb2', '/storage/pdf/12.pdf', '/storage/epub/12.epub', '/storage/audio/3.mp3'),
(13, '/storage/cover/13.jpg', '/storage/fb2/13.fb2', '/storage/pdf/13.pdf', '/storage/epub/13.epub', '/storage/audio/3.mp3'),
(14, '/storage/cover/14.jpg', '/storage/fb2/14.fb2', '/storage/pdf/14.pdf', '/storage/epub/14.epub', '/storage/audio/3.mp3'),
(15, '/storage/cover/15.jpg', '/storage/fb2/15.fb2', '/storage/pdf/15.pdf', '/storage/epub/15.epub', '/storage/audio/3.mp3'),
(16, '/storage/cover/16.jpg', '/storage/fb2/16.fb2', '/storage/pdf/16.pdf', '/storage/epub/16.epub', '/storage/audio/3.mp3'),
(17, '/storage/cover/17.jpg', '/storage/fb2/17.fb2', '/storage/pdf/17.pdf', '/storage/epub/17.epub', '/storage/audio/3.mp3'),
(18, '/storage/cover/18.jpg', '/storage/fb2/18.fb2', '/storage/pdf/18.pdf', '/storage/epub/18.epub', '/storage/audio/3.mp3'),
(19, '/storage/cover/19.jpg', '/storage/fb2/19.fb2', '/storage/pdf/19.pdf', '/storage/epub/19.epub', '/storage/audio/3.mp3'),
(20, '/storage/cover/20.jpg', '/storage/fb2/20.fb2', '/storage/pdf/20.pdf', '/storage/epub/20.epub', '/storage/audio/3.mp3'),
(21, '/storage/cover/21.jpg', '/storage/fb2/21.fb2', '/storage/pdf/21.pdf', '/storage/epub/21.epub', '/storage/audio/3.mp3'),
(22, '/storage/cover/22.jpg', '/storage/fb2/22.fb2', '/storage/pdf/22.pdf', '/storage/epub/22.epub', '/storage/audio/3.mp3'),
(23, '/storage/cover/23.jpg', '/storage/fb2/23.fb2', '/storage/pdf/23.pdf', '/storage/epub/23.epub', '/storage/audio/3.mp3'),
(24, '/storage/cover/24.jpg', '/storage/fb2/24.fb2', '/storage/pdf/24.pdf', '/storage/epub/24.epub', '/storage/audio/3.mp3'),
(25, '/storage/cover/25.jpg', '/storage/fb2/25.fb2', '/storage/pdf/25.pdf', '/storage/epub/25.epub', '/storage/audio/3.mp3'),
(26, '/storage/cover/26.jpg', '/storage/fb2/26.fb2', '/storage/pdf/26.pdf', '/storage/epub/26.epub', '/storage/audio/3.mp3'),
(27, '/storage/cover/27.jpg', '/storage/fb2/27.fb2', '/storage/pdf/27.pdf', '/storage/epub/27.epub', '/storage/audio/3.mp3'),
(28, '/storage/cover/28.jpg', '/storage/fb2/28.fb2', '/storage/pdf/28.pdf', '/storage/epub/28.epub', '/storage/audio/3.mp3'),
(29, '/storage/cover/29.jpg', '/storage/fb2/29.fb2', '/storage/pdf/29.pdf', '/storage/epub/29.epub', '/storage/audio/3.mp3'),
(30, '/storage/cover/30.jpg', '/storage/fb2/30.fb2', '/storage/pdf/30.pdf', '/storage/epub/30.epub', '/storage/audio/3.mp3');

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
'Ирландия'),
('Борис Акунин', '1956-05-20', 'Русский писатель, учёный-японист, литературовед, переводчик, общественный деятель. Также публиковался под литературными псевдонимами Анна Борисова и Анатолий Брусникин.',
'Россия'),
('Дэн Браун', '1964-06-22', 'Американский писатель, журналист, музыкант. Из-под его пера вышли такие бестселлеры, как «Ангелы и демоны», «Код да Винчи», «Утраченный символ» и «Инферно», рассказывающие о тайных обществах, символике, заговорах. Его первый роман «Цифровая крепость» посвящён деятельности Агентства национальной безопасности США.',
'США'),
('Анджей Сапковский', '1948-06-21', 'Польский писатель-фантаст и публицист, автор популярной фэнтези-саги «Ведьмак». Произведения Сапковского изданы на польском, чешском, русском, немецком, испанском, финском, литовском, французском, английском, португальском, болгарском, белорусском, итальянском, шведском, сербском, украинском и китайском языках. По заявлениям издателей, Сапковский входит в пятёрку самых издаваемых авторов Польши. Сам же писатель, как правило, не распространяется о тиражах своих книг.',
'Польша'),
('Маргарет Митчелл', '1900-11-08', 'Американская писательница и журналистка, автор романа «Унесённые ветром».',
'США'),
('Джоджо Мойес', '1969-08-04', 'Английская романистка и журналистка, двукратная обладательница премии Ассоциации романтических новеллистов в номинации «Романтическая новелла года» за романы «Заморские фрукты» и «Последнее письмо от твоего любимого».',
'Великобритания'),
('Татьяна Устинова', '1968-04-21', 'Современная российская писательница, сценаристка, переводчица и радиоведущая.',
'Россия'),
('Бодо Шефер', '1960-09-10', 'Современный немецкий эксперт по финансовым вопросам, бизнес-коуч, автор книг.',
'Германия'),
('Диана Уинн Джонс', '1934-03-26', 'Британская писательница, автор фантастических романов для детей и взрослых. Наиболее известные работы — это серия книг о Крестоманси и роман «Ходячий замок», а также «Темный Властелин Деркхольма».',
'Великобритания');

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
('литература 19 века'),
('исторический'),
('триллер'),
('любовный роман'),
('финансы'),
('сказки');

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
(4, 7),
(5, 3),
(5, 9),
(5, 5),
(6, 3),
(6, 9),
(6, 5),
(7, 3),
(7, 9),
(7, 5),
(8, 3),
(8, 9),
(8, 5),
(9, 3),
(9, 9),
(9, 5),
(10, 3),
(10, 9),
(10, 5),
(11, 3),
(11, 9),
(11, 5),
(12, 1),
(12, 2),
(12, 9),
(13, 1),
(13, 2),
(13, 9),
(14, 1),
(14, 2),
(14, 9),
(15, 1),
(15, 2),
(15, 9),
(16, 1),
(16, 2),
(16, 9),
(17, 1),
(17, 2),
(17, 9),
(18, 1),
(18, 2),
(18, 9),
(18, 11),
(19, 1),
(19, 2),
(19, 8),
(20, 1),
(20, 2),
(20, 8),
(20, 11),
(21, 1),
(21, 8),
(21, 12),
(22, 1),
(22, 12),
(22, 8),
(23, 1),
(23, 8),
(23, 12),
(24, 13),
(24, 11),
(24, 9),
(25, 13),
(25, 8),
(26, 1),
(26, 13),
(26, 8),
(27, 1),
(27, 8),
(28, 6),
(28, 14),
(28, 8),
(29, 14),
(29, 8),
(30, 15),
(30, 6),
(30, 5),
(30, 8);

INSERT INTO books_authors (book_id, author_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 7),
(6, 7),
(7, 7),
(8, 7),
(9, 7),
(10, 7),
(11, 7),
(12, 5),
(13, 5),
(14, 5),
(15, 5),
(16, 5),
(17, 5),
(18, 5),
(19, 5),
(20, 5),
(21, 6),
(22, 6),
(23, 6),
(24, 8),
(25, 9),
(26, 10),
(27, 10),
(28, 11),
(29, 11),
(30, 12);

COMMIT

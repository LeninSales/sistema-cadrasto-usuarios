<h1 align="center" style="font-weight: bold;">Sistema de Cadastro de Usuários</h1>

<p align="center">
<a href="#tech">Tecnologias</a> •
<a href="#started">Como Executar</a> •
<a href="#routes">Endpoints da API</a> •
<a href="#future">Melhorias Futuras</a>
</p>

<p align="center">
<b>Este projeto é uma API REST desenvolvida em <strong>Java com Spring Boot</strong>,
utilizando <strong>Spring Data JPA</strong> e <strong>Microsoft SQL Server</strong> como banco de
dados.<br>
A aplicação permite o <strong>cadastro, listagem e consulta de usuários</strong> com
persistência em banco relacional.
</b>
</p>

<h2 id="tech">💻 Tecnologias</h2>

<p>As seguintes tecnologias foram utilizadas no desenvolvimento do projeto:</p>

<ul>
<li><strong>Java 17</strong></li>
<li><strong>Spring Boot 3.5.4</strong>: Framework principal para a construção da API.</li>
<li><strong>Spring Data JPA</strong>: Para persistência de dados.</li>
<li><strong>Microsoft SQL Server</strong>: Banco de dados relacional.</li>
<li><strong>Hibernate</strong>: Implementação padrão do JPA.</li>
<li><strong>Lombok</strong>: Para simplificar a criação de classes.</li>
<li><strong>Maven</strong>: Gerenciador de dependências.</li>
</ul>

<h2 id="started">🚀 Como Executar</h2>

<p>Siga as instruções abaixo para configurar e executar o projeto localmente.</p>

<h3>Pré-requisitos</h3>
<ul>
<li><strong>Java Development Kit (JDK) 17+</strong></li>
<li><strong>Apache Maven 3.9+</strong></li>
<li><strong>Microsoft SQL Server</strong> instalado e rodando na porta padrão <strong>1433</strong>.</li>
<li><strong>Git</strong></li>
<li><strong>Postman</strong> ou ferramenta similar para testar os endpoints da API.</li>
</ul>

<h3>1. Clonando o Repositório</h3>
<p>Para clonar o projeto para sua máquina local, utilize o seguinte comando:</p>

<pre><code style="background-color: #f4f4f4; padding: 10px; border-radius: 5px; display: block;">
git clone https://github.com/seu-usuario/usuarios-formulario.git
</code></pre>

<h3>2. Configuração do Banco de Dados</h3>
<h4>2.1. Criação do Banco e Tabela</h4>
<p>Execute os comandos SQL abaixo para criar o banco de dados e a tabela de usuários no SQL Server:</p>

<pre><code style="background-color: #f4f4f4; padding: 10px; border-radius: 5px; display: block;">
-- Criar o banco de dados
CREATE DATABASE CadrastoUsuario;
GO
-- Usar o banco criado
USE CadrastoUsuario;
GO

-- Criar a tabela de usuários
CREATE TABLE dbo.usuario (
id_user UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
nome NVARCHAR(100) NOT NULL,
idade INT NOT NULL CHECK (idade > 0),
email NVARCHAR(100) NOT NULL UNIQUE,
data_cadastro DATETIME DEFAULT GETDATE()
);
GO
</code></pre>

<h4>2.2. Configuração das Credenciais</h4>
<p>Navegue até a pasta src/main/resources/ e crie um novo arquivo chamado application.properties. Copie o conteúdo do arquivo application.properties.example para ele e substitua as credenciais do banco de dados com suas informações.</p>

<h3>3. Iniciando a Aplicação</h3>
<p>Dentro do diretório raiz do projeto, use o comando Maven para iniciar a aplicação Spring Boot:</p>
<pre><code style="background-color: #f4f4f4; padding: 10px; border-radius: 5px; display: block;">
cd usuarios-formulario
mvn spring-boot:run
</code></pre>

<p>A aplicação será iniciada na porta padrão <strong>8080</strong>.</p>

<h2 id="routes">📍 Endpoints da API</h2>

<p>A API expõe os seguintes endpoints para gerenciamento de usuários:</p>

<table style="width:100%; border-collapse: collapse;">
<thead>
<tr>
<th style="border: 1px solid #ddd; padding: 8px; text-align: left;">Rota</th>
<th style="border: 1px solid #ddd; padding: 8px; text-align: left;">Descrição</th>
<th style="border: 1px solid #ddd; padding: 8px; text-align: left;">Método</th>
</tr>
</thead>
<tbody>
<tr>
<td style="border: 1px solid #ddd; padding: 8px;"><kbd>/usuarios/salvar</kbd></td>
<td style="border: 1px solid #ddd; padding: 8px;">Cadastra um novo usuário no banco de dados.</td>
<td style="border: 1px solid #ddd; padding: 8px;"><code>POST</code></td>
</tr>
<tr>
<td style="border: 1px solid #ddd; padding: 8px;"><kbd>/usuarios/listar</kbd></td>
<td style="border: 1px solid #ddd; padding: 8px;">Retorna a lista completa de todos os usuários cadastrados.</td>
<td style="border: 1px solid #ddd; padding: 8px;"><code>GET</code></td>
</tr>
<tr>
<td style="border: 1px solid #ddd; padding: 8px;"><kbd>/usuarios/{id}</kbd></td>
<td style="border: 1px solid #ddd; padding: 8px;">Busca um usuário específico através do seu ID (UUID).</td>
<td style="border: 1px solid #ddd; padding: 8px;"><code>GET</code></td>
</tr>
<tr>
<td style="border: 1px solid #ddd; padding: 8px;"><kbd>/usuarios/{id}</kbd></td>
<td style="border: 1px solid #ddd; padding: 8px;">Exclui um usuário do banco de dados através do seu ID.</td>
<td style="border: 1px solid #ddd; padding: 8px;"><code>DELETE</code></td>
</tr>
</tbody>
</table>


<br>

<h3>POST /usuarios/salvar</h3>
<h4>Corpo da Requisição (JSON):</h4>
<pre><code style="background-color: #f4f4f4; padding: 10px; border-radius: 5px; display: block;">
{
"nome": "João Silva",
"idade": 28,
"email": "joao@email.com"
}
</code></pre>
<h4>Resposta de Sucesso (JSON - Status 201 Created):</h4>
<pre><code style="background-color: #f4f4f4; padding: 10px; border-radius: 5px; display: block;">
{
"id": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
"nome": "João Silva",
"idade": 28,
"email": "joao@email.com"
}
</code></pre>
<p><i>Observação: O <code>id</code> e a <code>data_cadastro</code> são gerados automaticamente pelo sistema.</i></p>

<h2 id="boas-praticas">💡 Boas Práticas e Recursos</h2>
<ul>
<li>Uso de UUID como chave primária, garantindo unicidade global e evitando problemas de concorrência.</li>
<li>Validações integradas, como a garantia de que o campo email é único e os campos nome e idade são obrigatórios.</li>
<li>Seguindo o Padrão em Camadas (Controller, Service, Repository), que promove a separação de responsabilidades e facilita a manutenção do código.</li>
<li>Utilização de DTOs (Data Transfer Objects) para a comunicação entre as camadas, evitando a exposição do modelo de domínio diretamente.</li>
</ul>

<h2 id="future">🎯 Melhorias Futuras</h2>
<p>Esta é uma lista de possíveis melhorias e novas funcionalidades para o projeto:</p>
<ul>
<li>Implementar testes unitários e de integração.</li>
<li>Adicionar paginação à listagem de usuários para melhorar a performance em grandes volumes de dados.</li>
</ul>

<h2 id="autor">👨‍💻 Autor</h2>
<p>Desenvolvido por Lenin Sales<br>
📧 Email: leninsalesoliveira@gmail.com<br>
🔗 LinkedIn: <a href="https://www.linkedin.com/in/leninsales" target="_blank">linkedin.com/in/leninsales</a></p>

<h2 id="license">Licença 📃</h2>

Este projeto está sob a licença [MIT](./LICENSE).
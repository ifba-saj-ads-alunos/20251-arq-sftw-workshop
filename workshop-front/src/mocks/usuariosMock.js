const usuariosMock = [
  {
    id: 1,
    nome: "João da Silva",
    email: "joao@ifba.edu.br",
    senha: "123456",
    cpf: "123.456.789-00",
    perfil: "discente",
    pcd: true,
    tipoPCD: ["auditiva", "motora"],
    solicitacaoAcessibilidade: "Precisa de intérprete de Libras.",
    isAdmin: false,
    eventosInscritos: [1, 3],
    certificados: [101, 103]
  },
  {
    id: 2,
    nome: "Maria Oliveira",
    email: "maria@ifba.edu.br",
    senha: "abcdef",
    cpf: "987.654.321-00",
    perfil: "docente",
    pcd: false,
    tipoPCD: [],
    solicitacaoAcessibilidade: "",
    isAdmin: true,
    eventosInscritos: [2],
    certificados: [102]
  },
  {
    id: 3,
    nome: "Carlos Souza",
    email: "carlos@gmail.com",
    senha: "senha123",
    cpf: "321.654.987-00",
    perfil: "externo",
    pcd: true,
    tipoPCD: ["visual"],
    solicitacaoAcessibilidade: "Necessita de material ampliado.",
    isAdmin: false,
    eventosInscritos: [],
    certificados: []
  },
  {
    id: 4,
    nome: "Brenda Martinez",
    email: "1@1",
    senha: "1",
    cpf: "123.456.789-10",
    perfil: "discente",
    pcd: false,
    tipoPCD: [],
    solicitacaoAcessibilidade: '',
    isAdmin: false,
    eventosInscritos: [],
    certificados: []
  }
];

export default usuariosMock;

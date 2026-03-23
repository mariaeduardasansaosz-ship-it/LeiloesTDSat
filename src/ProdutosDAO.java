import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    // MÉTODO PARA CADASTRAR
    public void cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB();
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto.");
        }
    }

    // MÉTODO PARA LISTAR (Apenas um!)
    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produtos";
        conn = new conectaDAO().connectDB();
        listagem.clear(); // Limpa a lista antes de preencher para não duplicar
        
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(resultset.getInt("id"));
                p.setNome(resultset.getString("nome"));
                p.setValor(resultset.getInt("valor"));
                p.setStatus(resultset.getString("status"));
                listagem.add(p);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
        return listagem;
    }
}
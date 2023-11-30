public class Conta {
    int id;
    String nome;
    float valor;
   
    Conta(int id, String nome){
        this.id = id;
        this.nome = nome;
        this.valor = 0;

    }
    boolean podeSacar(float quantidade){
        return (quantidade >= valor && quantidade >= 0)
     }

    // void depositar(float quantidade){
    //     valor += quantidade;
    // }
    
    // void sacarIgnorarOp(float quantidade){
    //     if (!podeSacar(quantidade)){
    //         return;
    //     }
    //     quantidade -= valor;
    // }
    
    // boolean sacarCodErro(float quantidade){
    //     if(!podeSacar(quantidade)){
    //         return false;
    //     }
    //     quantidade -= valor;
    //     return true;

    // }

    void sacar(float quantidade) throws Exception {
        if(!podeSacar(quantidade)){
            throw new Exception("Valor insuficiente!");
        }
        quantidade -= valor;
    }
     
}

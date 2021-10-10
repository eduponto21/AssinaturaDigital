/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.utfpr.sistemasdistribuidos.assinaturadigital;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edupo
 */
/**
 * Hello world!
 *
 */
public class EsquemaInicial {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        //isso aqui é para garantir que o remetente foi quem de fato enviou a mensagem, envia uma mensagem e uma assinatura, qualquer um pode ler.
        //try/catch para caso não encontre o algorítmo MD5 ou DSA, para chave inválida, para assinatura inválida
        try {

            //O Message Digest são funções hash que geram código de tamanho fixo e unidirecional
            //Para utilizar o Message Digest basta obter uma instância do algoritmo a ser utilizado, passar a informação que desejamos criptografar e por fim realizar a criptografia.
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            //A classe Java responsável por gerar as assinaturas digitais é a Signature.
            //Para gerar as chaves públicas e privadas utilizamos a classe KeyPairGenerator. KeyPairGenerator também necessita de um algoritmo para gerar as chaves. 
            //Tanto as chaves quanto a assinatura devem ser geradas utilizando-se o mesmo algoritmo (no exemplo o DSA)
            Signature signature = Signature.getInstance("DSA");
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");

            //Após obtermos as chaves do KeyPairGenerator devemos inicializa-lo através do método initialize() que recebe um tamanho de chave e um número aleatório através da classe SecureRandom
            SecureRandom secureRan = new SecureRandom();
            kpg.initialize(512, secureRan);

            KeyPair kp = kpg.generateKeyPair();
            PublicKey pubKey = kp.getPublic();
            PrivateKey priKey = kp.getPrivate();

            //Agora que temos a nossa chave privada podemos utilizá-la no nosso objeto Signature
            signature.initSign(priKey);

            //Nesse momento já podemos utilizar o método update() para que a informação possa ser assinada ou verificada
            String mensagem = "Exemplo de mensagem";
            signature.update(mensagem.getBytes());

            //E por fim chamamos o método sign() para a geração da assinatura
            byte[] assinatura = signature.sign();

            //Com isso, o remetente agora precisa fornecer a chave pública juntamente com o dado a ser enviado e a assinatura correspondente.
            //O destinatário receberá a assinatura digital e deve ter acesso à chave pública para então validar a assinatura junto com o dado recebido utilizando a chave pública.
            Signature signature2 = Signature.getInstance("DSA");
            signature2.initVerify(pubKey);
            signature2.update(mensagem.getBytes());

            if (signature2.verify(assinatura)) {
                //Mensagem assinada corretamente
                System.out.println("Válido!");
            } else {
                //Mensagem não pode ser validada
                System.out.println("Inválido!");
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AssinaturaDigital.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AssinaturaDigital.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(AssinaturaDigital.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

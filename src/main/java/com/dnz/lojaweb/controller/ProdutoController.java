package com.dnz.lojaweb.controller;

import com.dnz.lojaweb.model.AvaliacoesEntity;
import com.dnz.lojaweb.model.ProdutoEntity;
import com.dnz.lojaweb.repository.AvaliacoesService;
import com.dnz.lojaweb.repository.ProdutoService;
import com.dnz.lojaweb.utils.SessionManager;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product")
public class ProdutoController {
    @Autowired
    ProdutoService ps;
    
    @Autowired
    AvaliacoesService as;
    
    @Autowired
    SessionManager sm;
    
    @PostMapping("/registerProduct")
    public String prodRegister(@ModelAttribute("produto") ProdutoEntity produto, @RequestParam("formImage") MultipartFile imgFile){
        try {
            if(imgFile.isEmpty()){
                produto.setImage(null);
            }else{
                byte[] imgBytes = imgFile.getBytes();
                produto.setImage(imgBytes);
            }
        } catch(IOException ex){
            return "redirect:/cadastrar-produto";
        }
        
        
        ps.saveProduto(produto);
        
        return "redirect:/cadastrar-produto";
    }
    
    @PostMapping("/editProduct")
    public String editProduct(@ModelAttribute("produto") ProdutoEntity updatedProduto, @RequestParam("formImage") MultipartFile imgFile){
        ps.updProduto(updatedProduto, imgFile);
        
        return "redirect:/editar-produto";
    }
    
    @PostMapping("/searchProduto")
    public String prodSearch(@RequestParam("tituloProduto") String tituloProduto){
        ProdutoEntity prodFound = ps.getProdutoByTitle(tituloProduto);
        if(prodFound != null){
            return "redirect:/product/detalhes/" + prodFound.getId();
        }else{
            return "redirect:/";
        }
    }
    
    @GetMapping("/detalhes/{id}")
    public String details(@PathVariable(value="id") Integer id, Model model, HttpSession session){
        AvaliacoesEntity newReview = new AvaliacoesEntity();
        List<AvaliacoesEntity> prodReviews = as.getFirst6Avaliacoes(id);
        ProdutoEntity prodFound = ps.getProdutoById(id);
        
        if(prodFound.getImage() != null){
            byte[] imgBytes = prodFound.getImage();
            String base64Image = Base64.getEncoder().encodeToString(imgBytes);
            prodFound.setBase64Image(base64Image);
        }
        
        newReview.setProduct(prodFound);
        
        model.addAttribute("navUser", sm.getLoggedUser(session));
        model.addAttribute("countReviews", as.getCountAvaliacoes(id));
        model.addAttribute("produto", prodFound);
        model.addAttribute("lstAvaliacoes", prodReviews);
        model.addAttribute("analise", newReview);
        return "details";
    }
    
    @GetMapping("/getProductInfo/{id}")
    @ResponseBody
    public ProdutoEntity getProductInfo(@PathVariable Integer id){
        ProdutoEntity prodDetails = ps.getProdutoById(id);
        
        if(prodDetails.getImage() == null){
            prodDetails.setBase64Image("D:/OneDrive/Documentos/NetBeansProjects/lojaweb/src/main/resources/static/images/defaultImg.jpg");
        }else{
            byte[] imgBytes = prodDetails.getImage();
            if(imgBytes != null){
                String base64Image = Base64.getEncoder().encodeToString(imgBytes);
                prodDetails.setBase64Image(base64Image);
            }
        }
        
        return prodDetails;
    }
    
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        ProdutoEntity prodToDelete = ps.getProdutoById(id);
        ps.deleteProduto(prodToDelete);
        
        return ResponseEntity.ok("/editar-produto");
    }
}

package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IProductoService;
import com.curso.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    // para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    // datos de la orden
    Orden orden = new Orden();

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("productos", productoService.findAll());
        return "usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model) {
        log.info("Id producto enviado como parametro {}", id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.get(id);
        producto = productoOptional.get();

        model.addAttribute("producto", producto);
        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();

        double sumaTotal = 0;

        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("Producto anadido: {}", optionalProducto.get());
        log.info("Cantidad: {}", cantidad);

        Producto producto = optionalProducto.get();
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        //validar que el producto no se anada 2 veces
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch( p -> p.getProducto().getId() == idProducto );

        if (!ingresado) {
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dto -> dto.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    // quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Integer id, Model model) {
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden: detalles) {
            if (detalleOrden.getProducto().getId() != id) {
                ordenesNueva.add(detalleOrden);
            }
        }

        //poner la nueva lista con los productos restantes a la eliminacion
        detalles = ordenesNueva;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dto -> dto.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model) {
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "/usuario/carrito";
    }

    @GetMapping("/order")
    public String order(Model model) {
        Usuario usuario = usuarioService.findById(1).get();

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);

        return "usuario/resumenorden";
    }
}

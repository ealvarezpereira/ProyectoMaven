/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quique.proyectomaven;

import java.io.IOException;
import javax.swing.JOptionPane;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;

/**
 *
 * @author quique
 */
public class Codigo {

    static GitHub github;

    public static void crearRepositorio() {

        try {
            github = GitHub.connectUsingPassword(InicioSesion.usu.getName(), InicioSesion.ctra.getName());
            String repoNombre = JOptionPane.showInputDialog("Introduzca el nombre del repositorio");
            GHCreateRepositoryBuilder repo = github.createRepository(repoNombre);
            repo.create();
        } catch (IOException ex) {
            System.out.println("Error repositorio. " + ex);
        }
    }

}

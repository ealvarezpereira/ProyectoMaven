/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quique.proyectomaven;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;

/**
 *
 * @author quique
 */
public class ClasePrincipal {

    /**
     * @param args the command line arguments
     */
    static GitHub github;

    public static void main(String[] args) throws IOException {

        github = GitHub.connect();
        String repoNombre = JOptionPane.showInputDialog("Introduzca el nombre del repositorio");
        GHCreateRepositoryBuilder repo = github.createRepository(repoNombre);
        repo.create();
        
    }


}

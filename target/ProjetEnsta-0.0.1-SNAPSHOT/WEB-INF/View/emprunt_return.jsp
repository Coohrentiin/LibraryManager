<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library Management</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="assets/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
  <jsp:include page='menu.jsp'></jsp:include>
  <main>
    <section class="content">
      <div class="page-announce valign-wrapper">
        <a href="#" data-activates="slide-out" class="button-collapse valign hide-on-large-only"><i class="material-icons">menu</i></a>
        <h1 class="page-announce-text valign">Retour d'un livre</h1>
      </div>
      <div class="row">
      <div class="container">
        <h5>Sélectionnez le livre à retourner</h5>
        <div class="row">
	      <form action="/LibraryManager/emprunt_return" method="post" class="col s12">
	        <div class="row">
	          <div class="input-field col s12">
	            <select id="id" name="id" class="browser-default">
	              <option value="" disabled selected>---</option>
                  <!-- DONE : parcourir la liste des emprunts non rendus et afficher autant d'options que nécessaire, sur la base de l'exemple ci-dessous -->
                    <!-- DONE : si l'attribut id existe, l'option correspondante devra être sélectionnée par défaut (ajouter l'attribut selected dans la balise <option>) -->
                    <% emprunts= (List) request.getAttribute("listeEmprunts"); %>
                    <c:forEach items="${emprunts}" var="emprunt">
                      <tr>
                        <% livreId = (int) emprunt.getIdLivre() %>
                        <% membreId = (int) emprunt.getIdmembre() %>
                        <% demprunt = emprunt.getDateEmprunt() %>
    
                        <% MembreService membreService=MembreServiceImpl.getInstance(); %>
                        <% LivreService livreService=LivreServiceImpl.getInstance(); %>
                        <% livre = (Livre) livreService.getById(livreId); %>
                        <% membre = (Membre) membreService.getById(membreId); %>
                        <td><%=livre.getTitre()%>, <em><%=livre.getAuteur%></em></td>
                        <td><%=membre.getNom%> <%=membre.getPrenom%></td>
                        <td><%=demprunt%></td>
                        <c:if test=${emprunt.getDateRetour()==null}>
                          <td>
                            <a href="emprunt_return?id=idDeLEmprunt">
                              <ion-icon class="table-item" name="log-in">
                            </a>
                          </td>
                        </c:if>
                        <c:if test=${emprunt.id!=null}>
                          <td>
                           <option selected value="idDeLEmprunt">"Titre du livre", emprunt� par Pr�nom et nom du membre emprunteur</option>
                          </td>
                        </c:if>
                      </tr>
                    </c:forEach>


                
                 
	            </select>
	          </div>
	        </div>
	        <div class="row center">
	          <button class="btn waves-effect waves-light" type="submit">Retourner le livre</button>
	          <button class="btn waves-effect waves-light orange" type="reset">Annuler</button>
	        </div>
	      </form>
	    </div>
      </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
  <script>
    document.addEventListener('DOMContentLoaded', function() {
	  var elems = document.querySelectorAll('select');
	  var instances = M.FormSelect.init(elems, {});
	});
  </script>
</body>
</html>

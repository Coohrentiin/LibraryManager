<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>Library Management</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"
    type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="assets/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
  <jsp:include page='menu.jsp'></jsp:include>
  <main>
    <section class="content">
      <div class="page-announce valign-wrapper">
        <a href="#" data-activates="slide-out" class="button-collapse valign hide-on-large-only"><i
            class="material-icons">menu</i></a>
        <h1 class="page-announce-text valign">Liste des emprunts</h1>
      </div>
      <div class="row">
        <div class="container">
          <div class="col s12">
            <table class="striped">
              <thead>
                <tr>
                  <th>Livre</th>
                  <th>Membre emprunteur</th>
                  <th>Date d'emprunt</th>
                  <th>Retour</th>
                </tr>
              </thead>

              <tbody id="results">
                <% emprunts= (List) request.getAttribute("emprunts"); %>
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
                    <c:if test=${emprunt.getDateRetour()!=null}>
                      <td>
                        <%=emprunt.getDateRetour()%>
                      </td>
                    </c:if>
                  </tr>
                </c:forEach>

                <!-- DONE : parcourir la liste des emprunts en cours et les afficher selon la structure d'exemple ci-dessus -->
                <!-- DONE : dans le champ "retour", afficher la date de retour si elle existe, et un lien vers la page de retour si la date est vide (comme dans l'exemple ci-dessus) -->
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>

</html>
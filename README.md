# Localization
 Localization API for HGLabor Network
 
`gradle wrapper`

` gradle publishToMavenLocal`
 
 You should create a **folder "lang"** containing json files
 
 Example: kitmessages_de.json
 
 Use **"#"** for indicating a new line
 Use **"${parameterName}"** for replacing values
 
 Localization.INSTANCE.getMessage()

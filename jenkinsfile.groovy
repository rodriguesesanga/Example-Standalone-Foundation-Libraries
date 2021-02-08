def nodeInfo() {
  println "Node: '${env.NODE_NAME}'"
}
def failed_message(link_gif){
  body="""
  <!DOCTYPE html>
  <html>
  <body style='font-family: sans-serif;'>
  <h1 align="center">&#x1F6A8; &#x1F6A8; &#x1F6A8; Build <code style="color:blue">#${env.BUILD_NUMBER}</code> in <i><code>${env.JOB_NAME}</code></i> failed! &#x1F6A8;&#x1F6A8;&#x1F6A8 </h1>
  </br>
  <h2 align="center">This failure comes from the running of that JOB on the label:  <i style="color: blue">Myparams</i><h2>
  <p style="color: red" align="center"><strong>Click on the link below to find out cause and try to fix it </strong></p>
  <p align="center">&#X1F517;<a href=${env.BUILD_URL}><code>${env.JOB_NAME}</code><code>#${env.BUILD_NUMBER}</code></a></p>

  <p align="center"><img src="https://www.microej.com/wp-content/uploads/2019/02/Product_SDK_mascotsdk.png" height="400" width="400"></p>
  </body>
  </html>
  """
  emailext body: "${body}" ,mimeType: 'text/html',subject: "Build failed in Jenkins: ${env.JOB_NAME}, #${env.BUILD_NUMBER}-FAILURE", to: 'rodrigue.sesanga@microej.com'
}

try{
 timestamps{
  node ('linux64'){
    stage ('CheckOut'){
        nodeInfo()
	cleanWs()
        checkout([$class: 'GitSCM', branches: [[name: 'feature1']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/rodriguesesanga/Example-Standalone-Foundation-Libraries.git']]])
    }
    stage('Find URLs from README') {
    	sh label: '', script: '''
                  ls
		  curl -L https://github.com/rodriguesesanga/Example-Standalone-Foundation-Libraries/blob/feature1/README.md > READMEcontent.txt
		  sed -n \'s/.*href="\\([^"]*\\).*/\\1/p\' READMEcontent.txt > url_href.txt
		  grep -o "^/[a-zA-Z0-9./?=_%:-]*" url_href.txt | sort -u >> url_without_http.txt
		  for line in $(cat url_without_http.txt);
		  do
		  	echo "https://github.com$line" >> url_file.txt
                  done
		  grep -o "https://[a-zA-Z0-9./?=_%:-]*" READMEcontent.txt | sort -u >> url_file.txt
		  grep -o "http://[a-zA-Z0-9./?=_%:-]*" READMEcontent.txt | sort -u >> url_file.txt'''
    }
    stage('Checking URL links') {
    	sh label: '', script: '''
		  for line in $(cat url_file.txt);
		  do
		  	if [ "$(curl -o /dev/null -s -w '%{http_code}\n' $line)" = "404" ];
			then
				echo $line >> error_url.txt
			elif [ "$(curl -o /dev/null -s -w '%{http_code}\n' $line)" -ne "200" ];
			then
				echo $line >> warning_url.txt
			else
				echo "$line : correct URL"
				
			fi
		  done'''
    }
    stage('Looking URLs error') {
	def contentFile = readFile.readFileLineByLine('error_url.txt')
	println("${contentFile}")

    }
  }
 }
}catch (e) {
  // In this case, the build failed
  currentBuild.result = "FAILURE"
  failed_message('https://media.giphy.com/media/h8ncU2Ps6b2zS/giphy.gif')
}

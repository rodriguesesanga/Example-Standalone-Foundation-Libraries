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
  <p align="center"><img src=${link_gif} height="200" width="400"></p>
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
  node ('public'){
    stage ('CheckOut'){
        nodeInfo()
        checkout([$class: 'GitSCM', branches: [[name: 'feature1']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/rodriguesesanga/Example-Standalone-Foundation-Libraries.git']]])
    }
    stage('Running Files') {
      if (isUnix()){
        sh label: '', script: '''
                  ls
		  content=$(curl -L https://github.com/rodriguesesanga/Example-Standalone-Foundation-Libraries/blob/feature1/README.md)
		  echo $content'''
      }else{
        bat label: '', script: '''
                   dir
                   type README.md'''
      }
    }
  }
 }
}catch (e) {
  // In this case, the build failed
  currentBuild.result = "FAILURE"
  failed_message('https://media.giphy.com/media/h8ncU2Ps6b2zS/giphy.gif')
}

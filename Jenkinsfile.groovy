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
  <h2 align="center">This failure comes from the running of that JOB on the label:  <i style="color: blue">${Node_ID}</i><h2>
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
    stage ('Node Informations'){
        nodeInfo()
    }
    stage('Running Files') {
        if (isUnix()){
                sh label: '', script: '''
                        ls'''
        }else{
                bat label: '', script: '''
                        dir'''
        }
    }
  }
 }
}catch (e) {
  // In this case, the build failed
  currentBuild.result = "FAILURE"
  if (Node_ID=='SRV2BULD124'){
    failed_message('https://media.giphy.com/media/h8ncU2Ps6b2zS/giphy.gif')
  }else{
      if (Node_ID=='SRV2BULD226'){
          failed_message('https://media.giphy.com/media/3oKIPs1EVbbNZYq7EA/giphy.gif')
      }else{
          if (Node_ID=='SRV4BULD117'){
                failed_message('https://media.giphy.com/media/TqiwHbFBaZ4ti/giphy.gif')
          }else{
              failed_message('https://media.giphy.com/media/iVDo6InQKyW8o/giphy.gif')
          }
      }
  }
}

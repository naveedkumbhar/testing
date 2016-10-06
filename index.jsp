<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="Resources/css/bootstrap.min.css">
<!-- <link rel="stylesheet" href="font-awesome.min.css">
 --><link rel="stylesheet" href="Resources/css/bootsnipp.min.css">
<script src="Resources/js/jquery-latest.min.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Upload</title>

<script type="text/javascript">

	function loadSpeechText() {
		var xmlhttp = new XMLHttpRequest();

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == XMLHttpRequest.DONE) {
				if (xmlhttp.status == 200) {
					
					debugger;
					var hiddenText= document.getElementById("responseTextHidden");
					var responseTextBox = document.getElementById("response");
						hiddenText.value = xmlhttp.responseText;
					var hiddenTextValue = hiddenText.value 
					var hiddenStoredTxt = document.getElementById("storedText");
					
					if(hiddenTextValue=="" || hiddenTextValue!=hiddenStoredTxt.value)
					{
						hiddenStoredTxt.value = hiddenTextValue;
						responseTextBox.value = hiddenTextValue;
						
					}

					return xmlhttp.responseText;
				} else if (xmlhttp.status == 400) {
					console.log('There was an error 400');
				} else {
					console.log('something else other than 200 was returned');
				}
			}
		};

		xmlhttp.open("POST", "SpeechServlet", true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send("action=getResult");
	}
	
</script>
</head>
<body>

	<script>
		function mouseDown() {

			var fullPath = document.getElementById("mic-icon").src;
			var filename = fullPath.replace(/^.*[\\\/]/, '');

			if (filename == "mic-icon-default.png") {
				document.getElementById("mic-icon").setAttribute('src',
						'Resources/img/mic-icon-start.png');

				startRecording();
			}

		}

		function mouseUp() {

			var fullPath = document.getElementById("mic-icon").src;
			var filename = fullPath.replace(/^.*[\\\/]/, '');

			if (filename == "mic-icon-start.png") {
				document.getElementById("mic-icon").setAttribute('src',
						'Resources/img/mic-icon-default.png');
				stopRecording()

				window.setInterval(function() {
					//alert("submiting form");
					loadSpeechText();
				}, 2000);

			}

		}
	</script>

	<div class="form-group col-lg-6">
		<h3 class="dark-grey">Speech to text</h3>

		<a href="#" id="toogleBtn" onmousedown="mouseDown()"
			onmouseup="mouseUp()" title="Please hold mic for recording">
			<img src="Resources/img/mic-icon-default.png" id="mic-icon"></a>
		<p id="output">Please click and hold mic button to record your voice</p>
		
		<textarea rows="6" cols="50" id="response"></textarea>
		
		<input type="hidden" id="responseTextHidden">
		<input type="hidden" id="storedText">
	</div>
	<script src="Resources/js/script.js"></script>
</body>
</html>
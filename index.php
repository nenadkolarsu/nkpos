<?php
/**
 * Copyright (C) 2013 peredur.net
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
include_once 'includes/db_connect.php';
include_once 'includes/functions.php';

sec_session_start();

if (login_check($mysqli) == true) {
    $logged = 'in';
} else {
    $logged = 'out';
}
?>
<!DOCTYPE html>
<html>
    <head>
        <title>Secure Login: Log In</title>
        <link rel="stylesheet" href="styles/main.css" />
	<link rel="stylesheet" href="styles/avatar.css" />
        <script type="text/JavaScript" src="js/sha512.js"></script> 
        <script type="text/JavaScript" src="js/forms.js"></script> 
		<script type="text/javascript" src="//e.issuu.com/embed.js" async="true"></script>
    </head>
    <body>
        <?php
        if (isset($_GET['error'])) {
            echo '<p class="error">Error Logging In!</p>';
        }
        ?> 
		<div data-configid="30247351/50904119" style="width:100%; height:1027px;" class="issuuembed"></div> 
	<div id="id01" class="modal">
        <form class="modal-content animate" action="includes/process_login.php" method="post" name="login_form"> 	
		<div class="imgcontainer">
		  <!--<span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
		   <img src="img_avatar2.png" alt="Avatar" class="avatar"> -->
		  <img src="images/avatar.png" class="avatar" alt="Prijava zaposlenih" height="200" width="100">
		</div>		
	    <div class="container">
                Email: <input type="text" name="email" placeholder="demo" required autofocus/>
            Password: <input type="password" name="password" id="password" placeholder="demo" required/>
			<div class="container" style="background-color:#cfd0d1"> <!-- #f1f1f1 -->
			<button type="submit">Login</button>
            <!-- <input type="button" value="Login" onclick="formhash(this.form, this.form.password);" />  -->
		<!--	<button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>  -->
                        <button type="button" id="b1" class="cancelbtn">Cancel</button>
			<span class="psw">Forgot <a href="#">password?</a></span>
                        
<!--                        <script>
                            myFun(){
                            $('form').attr('action','ltd/index.html');
                            }
                        </script>-->
                        
                        <script type="text/javascript">
                            document.getElementById("b1").onclick = function () {
                                $login=false;
                                location.href = "includes/logout.php";
//                                location.href = "ltd/index.php";
                            };
                        </script>
			</div>	
	    </div>
        </form>
	</div>
        <p>If you don't have a login, please <a href="register.php">register</a></p>
        <p>If you are done, please <a href="includes/logout.php">log out</a>.</p>
        <p>You are currently logged <?php echo $logged ?>.</p>
        <p>You are currently at <?php echo $logged ?>.</p>
    </body>
</html>

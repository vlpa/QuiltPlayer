@REM ----------------------------------------------------------------------------
@REM Copyright 2001-2004 The Apache Software Foundation.
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM      http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM ----------------------------------------------------------------------------
@REM

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of agruments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\javazoom\spi\vorbisspi\0.2\vorbisspi-0.2.jar;"%REPO%"\log4j\log4j\1.2.14\log4j-1.2.14.jar;"%REPO%"\commons-lang\commons-lang\2.2\commons-lang-2.2.jar;"%REPO%"\javazoom_mp3spi\mp3spi\1.9.4\mp3spi-1.9.4.jar;"%REPO%"\org\springframework\spring-beans\2.5.6\spring-beans-2.5.6.jar;"%REPO%"\commons-logging\commons-logging\1.1.1\commons-logging-1.1.1.jar;"%REPO%"\org\springframework\spring-core\2.5.6\spring-core-2.5.6.jar;"%REPO%"\commons-discovery\commons-discovery\0.2\commons-discovery-0.2.jar;"%REPO%"\org\castor\castor\1.2\castor-1.2.jar;"%REPO%"\org\tritonus\tritonus_share\0.3.6\tritonus_share-0.3.6.jar;"%REPO%"\org\springframework\spring-context-support\2.5.6\spring-context-support-2.5.6.jar;"%REPO%"\aopalliance\aopalliance\1.0\aopalliance-1.0.jar;"%REPO%"\org\springframework\spring-context\2.5.6\spring-context-2.5.6.jar;"%REPO%"\javazoom\jlgui\basicplayer\3.0\basicplayer-3.0.jar;"%REPO%"\org\jvnet\laf-widget\4.0dev\laf-widget-4.0dev.jar;"%REPO%"\javazoom\jl\jl\1.0.1\jl-1.0.1.jar;"%REPO%"\org\springframework\spring-aop\2.5.6\spring-aop-2.5.6.jar;"%REPO%"\com\quiltplayer\imagefetcher\1.0-SNAPSHOT\imagefetcher-1.0-SNAPSHOT.jar;"%REPO%"\junit\junit\4.5\junit-4.5.jar;"%REPO%"\com\thoughtworks\xstream\xstream\1.3.1\xstream-1.3.1.jar;"%REPO%"\xpp3\xpp3_min\1.1.4c\xpp3_min-1.1.4c.jar;"%REPO%"\com\quiltplayer\lyricsfetcher\1.0-SNAPSHOT\lyricsfetcher-1.0-SNAPSHOT.jar;"%REPO%"\javax\xml\jaxrpc-api\1.1\jaxrpc-api-1.1.jar;"%REPO%"\org\lyricswiki\lyricwiki\1.0\lyricwiki-1.0.jar;"%REPO%"\org\apache\axis\axis\1.4\axis-1.4.jar;"%REPO%"\org\neo4j\index-util\0.5\index-util-0.5.jar;"%REPO%"\com\jgoodies\forms\1.2.1\forms-1.2.1.jar;"%REPO%"\javax\wsdl\wsdl4j\1.5.1\wsdl4j-1.5.1.jar;"%REPO%"\com\xiph\jspeex\0.9.7\jspeex-0.9.7.jar;"%REPO%"\com\miglayout\miglayout\3.6.3\miglayout-3.6.3.jar;"%REPO%"\org\apache\lucene\lucene-core\2.4.0\lucene-core-2.4.0.jar;"%REPO%"\javax\xml\soap\saaj\1.2\saaj-1.2.jar;"%REPO%"\org\jvnet\laf-plugin\1.0\laf-plugin-1.0.jar;"%REPO%"\de\felixbruns\jotify\1.0-SNAPSHOT\jotify-1.0-SNAPSHOT.jar;"%REPO%"\tritonus_jorbis\tritonus_jorbis\0.3.6\tritonus_jorbis-0.3.6.jar;"%REPO%"\com\jcraft\jorbis\0.0.17\jorbis-0.0.17.jar;"%REPO%"\jakarta-regexp\jakarta-regexp\1.4\jakarta-regexp-1.4.jar;"%REPO%"\org\neo4j\neo4j\1.0-b7\neo4j-1.0-b7.jar;"%REPO%"\org\neo4j\neo4j-utils\1.0-b7\neo4j-utils-1.0-b7.jar;"%REPO%"\com\quiltplayer\id3utils\1.0-SNAPSHOT\id3utils-1.0-SNAPSHOT.jar;"%REPO%"\org\cmc\myid3\0.82\myid3-0.82.jar;"%REPO%"\org\farng\jid3lib\0.5.4\jid3lib-0.5.4.jar;"%REPO%"\asm\asm\2.2.2\asm-2.2.2.jar;"%REPO%"\javax\transaction\jta\1.1\jta-1.1.jar;"%REPO%"\org\springframework\spring-tx\2.5.6\spring-tx-2.5.6.jar;"%REPO%"\com\quiltplayer\quiltplayer\1.0-SNAPSHOT\quiltplayer-1.0-SNAPSHOT.jar:"%REPO%"\com\last\fm\bindings\1.0\bindings-1.0.jar
set EXTRA_JVM_ARGUMENTS=
goto endInit

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS% %EXTRA_JVM_ARGUMENTS% -classpath %CLASSPATH_PREFIX%;%CLASSPATH% -Dapp.name="quiltplayer" -Dapp.repo="%REPO%" -Dbasedir="%BASEDIR%" com.quiltplayer.QuiltPlayer %CMD_LINE_ARGS%
if ERRORLEVEL 1 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=1

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@endlocal

:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%

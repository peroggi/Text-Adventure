try{
var queststage = 0;
var currentloc;
var specialinput = false;
var inv = [];
var invalidverb = ["You can't do that.", "Shit nigger, what are you even trying to do?",
"If I let you do THAT, the game would break.", "Are you trying to cheat?"];
Array.prototype.pick = function(){
return this[Math.floor(Math.random() * this.length)];
};
Array.prototype.list = function(){
var str = "";
for(var i=0; i<this.length; i++){
if(this[i].links && !this[i].discovered){continue;}
str += this[i].name + ", ";}
if(!str){return "Nothing.";}
str = str.slice(0, str.length-2);
str +=".";
return str;
};
Array.prototype.contains = function(txt){
for(var i=0; i<this.length; i++){
if(txt==this[i].name.toLowerCase()){return this[i];}
}
return false;
};

//aliases
var writelist = /(^|\s)write($|\s)/gi;
var movelist = /((^|\s)move($|\s)|(^|\s)go\sto(%|\s))/gi;
var droplist = /(^|\s)drop($|\s)/gi;
var uselist= /(^|\s)use($|\s)/gi;
var invlist = /((^|\s)inv($|\s)|(^|\s)inventory($|\s))/gi;
var getlist=/(^|\s)get($|\s)/gi;
var looklist=/(^|\s)look($|\s)/gi;
var talklist=/(^|\s)talk($|\s)/gi;

//prototypes and constructors



//omnithing is a prototype object at the top of the diagram
var OmniThing = {
name: "",
desc: "",
getable: false,
get: function(){writeOut("You can't pick that up."); return false;},
look: function(){writeOut(this.desc); return true;},
talk: function(){writeOut("If you try to talk to that, people might think you are crazy."); return false;},
use: function(){
writeOut("I'm not going to let you use that for whatever sick, twisted purpose you have imagined."); return false;},
useon: function(user){
writeOut("Those two items don't go together"); return false;},
move: function(endloc, startloc){
if(typeof startloc==="undefined"){startloc = currentloc;}
startloc.contain.splice(startloc.contain.indexOf(this), 1);
endloc.contain.push(this);
return true;}
};

//below that are constructor functions which create an instance of an omni thing and give it the proper things needed to become a more specific object (person, location, etc)

//constructor functions take arguments like name and description and spit out a new instance
/* in another language that would look like one base class, t hen uh four classes that inherit from it and that's it

no three

person item and location */

// there is also the conversation class sort of which is off on it's own that creates conversation objects
function Convo(npc){
var chat = npc.chat;
var say;
if(npc.name=="Toast"){say ="";}
else{say = npc.name + " says: ";}
var asksubject = "<br/><br/>Type something to ask " +npc.name +" about it, or type cancel to stop talking.";
this.start = function(){
writeOut(say + this.searchchat("greeting", queststage) + asksubject);
};
this.searchchat = function(txt, qs, del){
if(typeof del==="undefined"){del = false;}
if(qs<0){return false;}
if(chat[qs]){
var chatstage = chat[qs];
if(qs==queststage){
for(x in chatstage.only){
if(x==txt){
if(del){chatstage.only[x] = [chat.invalid]; return true;}
return chatstage.only[x];}}}
for(x in chatstage.always){
if(x==txt){
if(del){chatstage.always[x] = [chat.invalid]; return true;}
return chatstage.always[x];}}
}
qs--;
return this.searchchat(txt, qs, del);
};
this.talking = function(txt){
if(txt=="cancel"){return false;}
if(a = this.searchchat(txt, queststage)){
writeOut(say + a[0] + asksubject);
if(a.length==2){
if(a[1]()=="delthis"){this.searchchat(txt, queststage, true);}}
return true;
}
return writeOut(say + chat.invalid + asksubject);
};
}
function Location(name, contain, desc){
var l = Object.create(OmniThing);
l.name = name;
l.links = [];
l.contain = contain;
l.discovered = false;
l.desc = desc;
l.remove = function(obj){
if(this.contain.indexOf(obj)){
this.contain.splice(this.contain.indexOf(obj), 1); return true;}
else{return false;}};
l.look = function(){
var txt = this.desc + " You can also see the following things: " + this.contain.list() + "<br/><br/>You know of the following links from this location: " + this.links.list();
return writeOut(txt);
};
l.joinloc = function(locs){
for(i=0; i<locs.length;i++){
this.links = this.links.concat(locs[i]);
locs[i].links = locs[i].links.concat(this);
}
return true;
};
return l;
}
function Person(name, desc, chat){
var p = Object.create(OmniThing);
p.name = name;
p.desc = desc;
p.quest = false;
p.chat = chat;
p.get= function(){
writeOut(this.name + " would not be very happy if you tried to put them in your inventory.");
return false;
};
p.talk = function(){
if(this.quest){
var questdialogue = this.chat.quest[this.quest];
if(questdialogue.length==2){questdialogue[1]();}
writeOut(this.name + " says: " + questdialogue[0]);
return true;}
specialinput = new Convo(this);
specialinput.start();
return true;
};
p.use = function(){
writeOut("Using people is wrong."); return false;};
p.useon = function(user){
writeOut(this.name + " does not want that.");};
p.kill = function(){
this.desc = "That is " + this.name + ". That person is dead.";
this.talk = function(){writeOut("You cannot talk to dead people."); return false;};};
return p;
}
function Item(name, desc){
var i = Object.create(OmniThing);
i.name = name;
i.getable=true;
i.held = false;
i.desc = desc;
i.get = function(){
if(this.getable){
if(this.held){writeOut("You already have that in your inventory, where else are you going to put it?"); return false;}
writeOut("You get " + name); 
this.held = true;
inv.push(this);
currentloc.contain.splice(currentloc.contain.indexOf(this), 1);
return true;}
else{writeOut("You can't pick that up."); return false;}
};
i.drop = function(){
writeOut("You drop " + name);
this.held = false;
currentloc.contain.push(this);
inv.splice(inv.indexOf(this), 1);
return true;
};
return i;
}
//writes all output to box
function writeOut(txt){
document.getElementById("output").innerHTML=txt;
return true;
}

function save(){
var expdate = "expires=Wed, 3 Dec 2014 23:00:00 UTC; ";
document.cookie = "420ta="+queststage+"; "+expdate+"path=/";
}
function load(){
var cookies = document.cookie.split(";");
var loadnum;
for(i=0; i<cookies.length; i++){
if(cookies[i].match(/420ta=/)){loadnum = cookies[i].replace(/420ta=/, ""); break;}}
switch(parseInt(loadnum)){
case 1:
initworld();
discover([redditAREA, memesAREA, catsAREA, nsasubredditAREA, todayilearnedAREA]);
howlieNPC.move(streamAREA, nsasubredditAREA);
oldmanNPC.move(testlandAREA, memesAREA);
oldmanNPC.quest = false;
queststage = 1;
redditdeath();
writeOut("Save file loaded");
catsAREA.remove(catpictureITEM);
nsasubredditAREA.remove(lighterITEM);
inv.push(lighterITEM);
break;
case 2:
initworld();
discover([shesaidAREA, shesaidarchiveAREA, reichAREA]);
kojNPC.move(streamAREA, shesaidarchiveAREA);
shesaiddeath();
queststage = 2;
writeOut("Save file loaded");
reichAREA.remove(naziflagITEM);
break;
default: writeOut("There is no file to load from. This game only saves at certain checkpoints."); break;
}
}
function delsave(){
document.cookie = "420ta=-1";
writeOut("Save file deleted");
}
function discover(places){
for(i=0; i<places.length; i++){
places[i].discovered = true;}
return true;}

//quest events
function redditdeath(){
writeOut("You post your well written article on the wall of the subreddit. A few people crowd around and observe the post, their eyes going wide as they read. Word spreads quickly and the self-important Redditors begin screaming as their self-image collapses around them. After a few moments, several of them being to commit suicide.");
philtardNPC.kill(); avgredditorNPC.kill(); 
redditAREA.desc = reddit.altdesc; memesAREA.desc = memes.altdesc; 
todayilearnedAREA.desc = todayilearned.altdesc; catsAREA.desc = cats.altdesc;
oldmanNPC.quest = "redditquest";
}
function shesaiddeath(){
shaleenaNPC.kill();
lilithiumNPC.kill();
shesaidAREA.contain.push(keyojITEM);}
//Input handlers

//that's what the special input thing in the textIn function is for
// if a conversation object exists all input gets diverted to that object, which handles things
// if a conversation object exists all input gets diverted to that object, which handles things
function textIn(ui){
document.getElementById("gamein").value="";
ui = ui.toLowerCase();
if(specialinput){
if(!specialinput.talking(ui)){specialinput=false; currentloc.look();}
return true;
}
if(ui=="help"){return writeOut(help);}
switch(true){
case ui=="delete": delsave();  break;
case ui=="load": load(); break;
case looklist.test(ui): look(ui.replace(looklist, "")); break;
case getlist.test(ui): get(ui.replace(getlist, "")); break;
case talklist.test(ui): talk(ui.replace(talklist, "")); break;
case invlist.test(ui): writeOut("Your inventory contains:<br/><br/>" + inv.list()); break;
case uselist.test(ui): use(ui.replace(uselist, "")); break;
case movelist.test(ui): move(ui.replace(movelist, "")); break;
case droplist.test(ui): drop(ui.replace(droplist, "")); break;
case writelist.test(ui): writepost(ui.replace(writelist, "")); break;
case ui=="perogi is great": inv.push(naziflagITEM); inv.push(feministuniformITEM); shesaidarchiveAREA.discovered = true; break;
default: writeOut(invalidverb.pick()); break;
}
return true;
}
function look(txt){
if(!txt){return currentloc.look();}
var itemarr = currentloc.contain.concat(inv);
if(o = itemarr.contains(txt)){return o.look();}
writeOut("You can't look at things that don't exist.");
return 0;
}
function get(txt){
if(!txt){writeOut("You can't pick up nothing, faggot."); return false;}
var itemarr = currentloc.contain.concat(inv);
if(o = itemarr.contains(txt)){return o.get();}
writeOut("Whatever you're trying to pick up, it does not exist");
return false;
}
function talk(txt){
if(!txt){writeOut("You talk to yourself for a while. It was a riveting conversation."); return 0;}
var itemarr = currentloc.contain.concat(inv);
if(o = itemarr.contains(txt)){
return o.talk();}
writeOut("You try to talk to something that isn't there. Have you taken your medication today?");
return false;
}
function use(txt){
if(!txt){writeOut("You have to pick something to use"); return false;}
var itemarr = currentloc.contain.concat(inv);
var items = [];
for(i = 0; i<itemarr.length; i++){
if(txt.match(itemarr[i].name.toLowerCase())){items.push(itemarr[i]);}}
if(items.length==2){return useon(txt, items);}
if(items.length==1 && items[0].name.toLowerCase()==txt){return items[0].use();}
writeOut("You have tried to use something that exists only in your imagination.");
return false;
}
function useon(txt, items){
var user;
var usee;
if(txt.indexOf(items[0].name.toLowerCase())<txt.indexOf(items[1].name.toLowerCase())){
user = items[0]; usee = items[1];}
else{user = items[1]; usee = items[0];}
return usee.useon(user);
}
function drop(txt){
if(!txt){writeOut("You drop nothing. Now no one can say you are clumsy."); return false;}
if( o = inv.contains(txt)){return o.drop();}
writeOut("You can't drop something you don't possess.");
return false;
}
function move(txt){
if(!txt){writeOut("You shuffle around aimlessly a bit.");return false;}
if(o = currentloc.links.contains(txt)){
if(o.discovered){
o.look();
currentloc = o;
return true;}
}
writeOut("You can't travel there until you unlock teleportation.");
return false;
}
function writepost(txt){
switch(true){
case txt=="reddit" && redditAREA.discovered: writeOut("You compose an eloquent post about the failings of Reddit");
inv.push(redditpostITEM); break;
default: writeOut("You don't want to write about that"); break;}
return true;}


//NPC instances
var perogiNPC = Person("Perogi", perogi.desc, perogi.chat);
var toastNPC = Person("Toast", toast.desc, toast.chat);
toastNPC.useon = function(user){
if(user==catpictureITEM){
writeOut("You give toast the cat picture, which he accepts. You see a twinge of emotion on his face, he seems happy.<br/><strong>Toast has given you a cat flair.</strong>");
inv.splice(inv.indexOf(user), 1);
inv.push(catflairITEM);
return true;}
writeOut("Toast does not want that."); 
return false;};
var howlieNPC = Person("Howlie", howlie.desc, howlie.chat);
howlieNPC.useon = function(user){
if(user==lighterITEM){
writeOut("Howlie says: Oh that's my lighter! I must have dropped it at Reddit. Uh, you can keep it :) as thanks for saving me. You'll probably find a use for it too!"); return true;}
writeOut("Howlie does not want that."); return false;};
var kojNPC = Person("Koj", koj.desc, koj.chat);
kojNPC.useon = function(user){
if(user==keyojITEM){
writeOut("Koj says: thanks bro, just wish i coulda seen my bros kill dem wimmin bitchs, whatever. im goin back to the streams now ltr fgt.");
kojNPC.move(streamAREA, shesaidarchiveAREA);
inv.splice(inv.indexOf(keyojITEM), 1);
queststage++;
save();
return true;}
else{writeOut(this.name + " does not want that."); return false;}};
var avgredditorNPC = Person("Avg Redditor", avgredditor.desc, avgredditor.chat);
var torguardNPC = Person("Tor Guard", torguard.desc, torguard.chat);
var philtardNPC = Person("Philtard", philtard.desc, philtard.chat);
var catladyNPC = Person("Cat Lady", catlady.desc, catlady.chat);
catladyNPC.useon= function(user){
if(user==catflairITEM){
writeOut("Cat Lady says: eeeeeee omg! Thanks so much! Here you can have my flair for todayilearned, I'm so bored of that subreddit! Thaaanks :)<br/><strong>The Cat Lady has given you a todayilearned flair.</strong>");
inv.splice(inv.indexOf(catflairITEM, 1));
inv.push(todayilearnedflairITEM);
return true;}
if(user==catpictureITEM){
writeOut("Cat Lady says: AWWWWWW! So kyoot :3 you can keep it though, there are lots of cat pictures here");
return true;}
writeOut(this.name + " does not want that."); return false;};
var oldmanNPC = Person("Old Man", oldman.desc, oldman.chat);
oldmanNPC.useon = function(user){
if(user==todayilearnedflairITEM){
writeOut("Old Man says: Hehe, yes that will do nicely. Now you just have to <em>write</em> a hilarious post for <em>Reddit</em>, stamp it with that, and post it on the subreddit. That ought to be hilarious enough, hrk.");
return true;}
if(user==catflairITEM){
writeOut("Old Man says: hrgh, the hell you doing with that? Posting something in the cat subreddit isn't going to do anything, get a better flair."); return true;}
writeOut(this.name + " does not want that");
return false;};

//chapter 1
var sachsenNPC = Person("Sachsen", sachsen.desc, sachsen.chat);
var racenationNPC = Person("RaceNation", racenation.desc, racenation.chat);
var lilithiumNPC = Person("Lilithium", lilithium.desc, lilithium.chat);
var shaleenaNPC = Person("Shaleena", shaleena.desc, shaleena.chat);

//Item instances
//chapter 0
dildoITEM = Item("dildo", dildo.desc);
dildoITEM.use = function(){writeOut("I'm sure you'd like to do that, but you're not allowed");
return true;};
var jointITEM = Item("joint", joint.desc);
jointITEM.use = function(){writeOut("You smoke just a bit of the joint before putting it out. You get the feeling you might need it later."); return true;};
var catpictureITEM = Item("cat picture", catpicture.desc);
catpictureITEM.use = function(){writeOut(catpicture.desc + " Maybe someone would want this item?");
return true;};
var catflairITEM = Item("cat flair", catflair.desc);
catflairITEM.use = function(){
writeOut("This can be used to stamp posts with a flair on the Cats subreddit, indicating the post is from a trusted user."); 
return true;};
var todayilearnedflairITEM = Item("todayilearned flair", todayilearnedflair.desc);
todayilearnedflairITEM.use = function(){
writeOut("This can be used to stamp posts with a flair on the todayilearned subreddit, indicating the post is from a trusted user.");
return true;};
var redditpostITEM = Item("reddit post", redditpost.desc);
redditpostITEM.use = function(){
if(this.flaired && currentloc==todayilearnedAREA){
redditdeath();
return true;}
if(!this.flaired && currentloc==todayilearnedAREA){
writeOut("No one will believe this outrageous post unless it has a flair"); return false;}
writeOut("You can't post that here");
return false;};
redditpostITEM.useon = function(user){
if(user==todayilearnedflairITEM){
writeOut("You stamp the post with your flair. People will now assume a trusted user composed this post.");
this.flaired = true;
return true;}
if(user==catflairITEM){
writeOut("Using that flair on this post wouldn't accomplish anything"); return false;}
writeOut("Those two items don't go together"); return false;};
//chapter 1
var naziflagITEM = Item("Nazi flag", naziflag.desc);
naziflagITEM.getable = false;
naziflagITEM.get = function(){
if(this.getable){
if(this.held){writeOut("You already have that in your inventory, where else are you going to put it?"); return false;}
writeOut("You get " + this.name); 
this.held = true;
inv.push(this);
currentloc.contain.splice(currentloc.contain.indexOf(this), 1);
return true;}
else{writeOut("You try to get the flag but Sachson puts a hand on your shoulder and shakes his head at you."); return false;}
};
naziflagITEM.useon = function(user){
if(user==lighterITEM){
if(currentloc==reichAREA){
if(inv.indexOf(feministuniformITEM)!=-1){
writeOut("You quickly light the flag and run off, enraged Nazis are hot on your tail. As soon as you get back into the internet backbone, you ditch your disguise. The Nazis run right past you and into blogger.");
inv.splice(inv.indexOf(naziflagITEM),1);
inv.splice(inv.indexOf(feministuniformITEM), 1);
shesaiddeath();
return true;
}
else{ 
writeOut("If you lit the flag on fire without a disguise, the Nazis would lynch you on the spot.");
return false;}}
else{
writeOut("Burning the flag here wouldn't do anything.");
return false;}}
else{
writeOut("Those two items don't go together"); return false;}};
var lighterITEM = Item("lighter", lighter.desc);
var feministpostITEM = Item("feminist post", feministpost.desc);
feministpostITEM.use = function(){
if(currentloc==reichAREA){
writeOut("While no one is looking, you discretely post the feminist message in a corner of the forums. It takes a while, but once the Reich members notice they flock around it and become infuriated.");
inv.splice(inv.indexOf(this), 1);
lilithiumNPC.quest = "feministpost";
naziflagITEM.getable = true;
return true;}
else{writeOut("You can't post that here."); return false;}};
var feministuniformITEM = Item("feminist uniform", feministuniform.desc);
var keyojITEM = Item("key", keyoj.desc);

//Location instances
initworld = function(){
redditAREA = Location("Reddit", [avgredditorNPC], reddit.desc);
memesAREA = Location("Memes", [oldmanNPC], memes.desc);
catsAREA = Location("Cats", [catpictureITEM, catladyNPC], cats.desc);
todayilearnedAREA =  Location("Todayilearned", [philtardNPC], todayilearned.desc);
nsasubredditAREA = Location("NSA Subreddit", [howlieNPC, lighterITEM], nsasubreddit.desc);
testlandAREA = Location("Test Land", [], "This is for testing things how did you get here, go away.");

torgateAREA = Location( "Tor Gate", [torguardNPC], torgate.desc);
silkroadAREA = Location("Silkroad", [], silkroad.desc);

backboneAREA = Location("Internet Backbone", [], backbone.desc);
streamAREA = Location("420streams", [dildoITEM, jointITEM, perogiNPC, toastNPC], stream.desc);
bloggerAREA = Location("Blogger", [], blogger.desc);
discover([streamAREA, torgateAREA, backboneAREA, bloggerAREA]);

reichAREA = Location("Reich Forum", [sachsenNPC, naziflagITEM, racenationNPC], reich.desc);
shesaidAREA = Location("She Said", [lilithiumNPC, shaleenaNPC], shesaid.desc);
shesaidarchiveAREA = Location("She Said archive", [kojNPC,feministuniformITEM], shesaidarchive.desc);

backboneAREA.joinloc([redditAREA, streamAREA, torgateAREA, reichAREA, bloggerAREA]);
redditAREA.joinloc([memesAREA, catsAREA, todayilearnedAREA, nsasubredditAREA]);
bloggerAREA.joinloc([shesaidAREA]);
shesaidAREA.joinloc([shesaidarchiveAREA]);
torAREA.joinloc([silkroadAREA]);

currentloc = streamAREA;
inv = [];
queststage = 0;
}
initworld();
}
catch(err){alert(err);}

var help = "This is a text adventure game. You interact with things by typing a verb and then the thing you wish to interact with. Some of the important verbs are: look, get, drop, use, inventory, talk, and move. This isn't an inclusive list though, part of the fun of text adventures is experimenting! <br/>Some other important things: typing just \"look\" will have you look at the area you're currently in, and you can also use items on other items/people.<br/>The game will save at certain checkpoints. To load your game, type load. To delete your save type delete.<br/><br/>Good luck!";

//--NPCs--
//Perogi
var perogi = {
chat:{
invalid:"I have no strong feelings on that subject.",
0:{

always: {
greeting:["Yes, hi. Welcome to 420streams. Things are a little bare right now because everyone got <em>kidnapped</em> by the <em>NSA</em>."],
nsa:["They are the bad guys. They arrested a bunch of us for doing illegal things. Only me and toast were <em>not kidnapped</em>."],
"not kidnapped":["Yeah Toast and I are the only ones who didn't get taken by the NSA. Dunno why, probably because neither of us really do much, let alone illegal things."],
toast:["Toast is pretty cool. He doesn't do much really. In case you haven't noticed, he communicates using only pictures of cats."],
perogi:["What? You just want me to tell you everything about myself? If you want to get to know my character you're going to have to do more than just fucking ask me about myself. Honestly."],
tor: ["Tor is fantastic. I love the idea of cryptography. Specifically how it takes power away from centralized authorities, and can empower decentralized organizations. That's like, paradigm raping shit right there."],
"420streams": ["Yes that is this place. I fucking love it here it is fantastic. I won't go into all the wishy-washy philosophical reasons, because those aren't really important anyway. This is just a wonderful place to get drunk or high and watch really bad movies with cool people."]},
only: {
kidnapped:["Yeah the NSA was spying on us and eventually got most of the 420streamers arrested. You should go find them, you'd probably want a <em>guide</em> though."],
guide:["Me? No I'm not going to be your guide. I made this game so that really wouldn't be fair if I just told you everything, would it? Just go find Howlie, he'll probably be your guide. He's imprisoned somewhere on the internet, in <em>Reddit</em> I think. Just go to Reddit and try asking people about the NSA or howlie."],
reddit:["Yes Reddit, it's this awful place on the internet that I hate. Oh you mean how do you get there? Oh that's right, you need to know the appropriate links to get places. Here's the link to Reddit. From here go to the internet backbone, and from there you can get to Reddit. Now please leave, I am very busy drinking and watching television.<br/><strong>You received a link! You can now travel to Reddit</strong>.", function(){redditAREA.discovered = true; return "delthis";}]}},

1: {

always: {
greeting: ["Oh you're back. I see you rescued Howlie. Wonderful. Go bother Howlie now."],
reddit: ["I'm actually pretty happy with your... exploits in reddit. Pretty hilarious if you ask me."],
howlie: ["Howlie is nice. He streams movies of a wide variety, he has a thing for found footage horror films though."],
koj: ["Frankly he's fucking annoying. He yells and rages all the time. Occasionally his screaming rants are amusing and this is his only redeeming quality."],
"old man": ["I think I know who you're talking about, I've ran into him before. He's a strange character, he's got some secrets I think."]},
only: {}

}},
desc:"That is Perogi. He is staring blankly at a series of TV screens on the wall. They are playing static. Perogi is drinking from a bottle of liquor."
};
//Toast
var toast = {
chat:{
invalid:"Toast shuffles through the pile of cat pictures and holds up a picture of a cat. The cat looks confused.",
0: {

always: {
greeting:"Toast shuffles through the pile of pictures and holds up a picture of a cat. The cat is waving.",
perogi:["Toast shuffles through the pile of cat pictures and holds up a picture of a cat. The cat seems slightly discontent"],
toast:["Toast shuffles through the pile of cat pictures and holds up a picture of a cat. The cat is sad."],
nsa:["Toast shuffles through the pile of cat pictures and holds up a picture of a cat. The cat is afraid."],
"420streams":["Toast shuffles through the pile of cat pictures and holds up a picture of a cat. The cat is smiling"],
howlie: ["Toast shuffles through the pile of cat pictures and holds up a picture of a cat. The cat is looking around as if searching for something missing."],
reddit: ["Toast shuffles through the pile of cat pictures and holds up a picture of a cat. The cat is happy."],
tor: ["Toast shuffles through the pile of cat pictures and holds up a picture of a cat. The cat is pawing at an onion."]},
only: {
cats: ["Toast shuffles through the pile of cat pictures and holds up a picture. On the picture is a link to a subreddit about cats<br/><strong>You received a link! You can now travel to the Cats subreddit.</strong>", function(){catsAREA.discovered = true; return "delthis";}]}},

1: {
always: {
howlie: ["Toast shuffles through the pile of cat pictures and holds up a picture of two cats. The cats are happy and dancing."],
koj: ["Toast shuffles through the pile of cat pictures and holds up a picture of two cats. One cat is looking dissaprovingly at the other cat, which is causing a ruckus."]},
only: {}}

},
desc:"Toast is sitting in the corner, patiently sifting through a large pile of glossy cat pictures. Toast is a large man. He has no shirt on."
};

//Howlie
var howlie = {
chat: {
invalid: "Huh? idk what you're talking about.",
0: {

always: {
greeting: "o hey. How'd you get in here? dey told me I'd have this place to myself to work on mah <em>drawings</em>."},
only: {
drawings: ["Yeah some guys in suits told me they wanted to hire me to do mah furry drawings :3 so dey brought me here and I just been drawin."],
nsa: ["The NSA? wat. Are you sure? dey said dey were gonna pay me! But you're saying they <em>kidnapped</em> me?"],
kidnapped: ["Everyone else has been kidnapped? O gawd they must have tricked me! Mmmmkay imma go back to 420streams then, meet yah back there!<br/><br/><strong>You have reached a checkpoint!</strong>", 
function(){streamAREA.contain.push(howlieNPC);
nsasubredditAREA.contain.splice(nsasubredditAREA.contain.indexOf(howlieNPC), 1);
specialinput=false;
queststage++;
save();
return "delthis";}]}},

1: {

always: {
greeting: ["o hi! Thanks for saving me, stupid NSA tricked me D: now we have to rescue everyone else. You should look for <em>koj</em> next."],
perogi: ["Perogi is a fgt. He mostly just gets drunk and yells about silly shit, he likes to cause trouble and mess with people."],
toast: ["Toast is nice :3 he's really level headed when he actually talks, which isn't often any more I don't think. He's pretty crazy like all of us just in a different way I guess, he doesn't feel comfortable talking."],
furry: ["Yes! I luv animals, they so cute! My fursona is a coyote with wings :3"],
nsa: ["Fuck dos guys! I drew all those cute porn drawings for nothing! They said they were gonna pay me :("],
"old man": ["An old guy on the internet? uh, I'm sure that's not too common but idk who your talking about."],
reddit: ["o mah gawd! I saw all the dead people when I was leaving! Did you do that? D:"],
tor: ["idk much about tor, I never go there. I just hear it has lots of cp and guns and stuff, I streamed a doc about it once. It was neat."],
"420streams": ["Yes! I luv it here :3 me and cj founded this around October 2010. We fight a lot, mostly because splagh and perogi think it's fun to argue, but we all luv each other :3"]},
only: {
koj: ["its short for King Orange Juice. Everyone just calls him <em>koj</em> though. You should start asking about him on this <em>Nazi forum</em> he goes to. I warn you tho he is really mean :("],
"nazi forum": ["It's called Reich Forum I think. He left the link around here somewhere I think, oh yeah here it is!<br/><strong>You have recieved a link! You can now travel to the Reich Forums</strong>", function(){reichAREA.discovered = true; return "delthis";}]
//disa: ["Disa mostly hangs out here and at this weird occult forum place. He's into occultism n weird stuff. Also he's pretty crazy, he is <em>schizo</em>, just so yah know. I think he left the link to that forum around here somewhere... o here it is!<br/><strong>You received a link! You can now travel to the occult forums.</strong>"],
//sue: ["Sue mostly hangs out with disa, they do weird stuff. I don't think sue is crazy... just weird :/ anyway he's probably wherever disa is."],
//schizo: ["yuh he crazay. It's not a big deal though, pretty much everyone at 420streams has been to an institution or prison at some point, so whateva. I was in one for a while, I'm bipolar and a big schizo I guess. The government gives me free money now tho :3"]
}

}},
desc: "That is Howlie, a 420streamer. He has coyote ears and a tail; he is a furry. He always appears to be smiling sweetly."};

//Koj
var koj = {
chat: {
invalid: "you have fuckin downs idk wat you talkin about.",
1:{

always: {
nsa: ["those fucking fucks are everything wrong with amurica, we should stone all of them."],
perogi: ["perogi is fuckin idiot, he thinks he knows everything but hes just a fgt."],
toast: ["that guy is fuckin weird he doesnt even talk anymore, idk what happened."],
howlie: ["he is furry, fuckin furries man. whatever."],
koj: ["oh i wonder who that is, ITS ME YOU FUCK WHY ARE YOU ASKING ME ABOUT MYSELF?"]
 },
only:{
greeting: ["what the fuck are you looking at? <em>help</em> me you mouth-breathing lizard-lunch!"],
help: ["YES im fucking chained here, does this look fun? find that <em>evil whore</em> and get the key from her."],
"evil whore": ["Lilthium or w/e fuck her name is"]}

}},
desc: "That is King Orange Juice. He is young and angry at the world. The vast majority of things that come out of his mouth are snarky and hateful."};

//Average Redditor
avgredditor = {
chat: {
invalid: "There isn't a subreddit about that topic, so I don't know anything about it.",
0: {

always: {
greeting: ["Hey, man. Welcome to <em>Reddit</em>. This is where people swap interesting life stories and ideas, it's like, progressive, man. Check out some cool <em>subreddits</em> while you're here."],
reddit: ["That's this place, dude. Here people like, interact with each other and figure out social issues, you know? People also make lots of funny and witty <em>jokes</em> and post them in <em>subreddits</em>."],
nsa: ["Those guys are like, all backwards man! They can't just like, spy on their own citizens. Liberty, man!"],
howlie: ["Who? Neverhead of him, man"]},
only: {
subreddits: ["A subreddit is like, about a specific topic. My favorite is todayilearned. It has like, cool stories about people learning in their daily lives, you know? Everybody should post there and like, learn stuff everyday, man. Here, I'll give you the link.<br/><strong>You received a link! You can now travel to the todayilearned subreddit.</strong>", function(){todayilearnedAREA.discovered = true; return "delthis";}],
jokes: ["Yeah really funny, witty jokes, man. We call them 'memes' and they're really cool. Let me give you the link to the memes subreddit, dude. You'll thank me.<br/><strong>You received a link! You can now travel to the memes subreddit.</strong>", function(){memesAREA.discovered = true; return "delthis";}]}

}},
desc: "That is your average Redditor. He is a white male between 25 and 35 years old. He appears to have a high opinion of himself."
};

//Philtard (redditor)
var philtard = {
chat: {
invalid: "I don't know what you're talking about, but maybe today I will learn it!",
0: {

always: {
greeting: ["Hello! I spend all my free time here because distant relationships with people with shared interests feels fulfilling. Did you know nursing homes in Germany have fake bus stops?"],
nsa: ["The NSA sucks man, they made me lose my faith in humanity. Did you know that we use the abbreviation X-mas because X is greek for Christ?"],
howlie: ["Howlie? He sounds like a furry, they make me lose my faith in humanity. If he's a furry I'd check the <em>Cats</em> subreddit. Did you know flight attendants don't get paid their full salary until the plane doors close?"],
cats: ["If there are dirty furries on Reddit, that's where they'd be. I don't have a link for you though, try asking someone who loves cats. Did you know that female koalas have lesbian sex?"]},
only: {}

}},
desc: "That is a Redditor named Philtard.  He is slightly overweight and has a neckbeard. Philtard is intently scanning posts tacked onto the wall, he is drooling slightly."};

//Cat Lady (redditor)
var catlady = {
chat: {
invalid: "Um! What does that mean? XD",
0: {

always: {
greeting: ["Hiiiii there! Did you come here cause you liek cats? :3 omg I love <em>cats</em>."],
cats: ["eeeeee! Aren't cats the best? I could just look at them ALL DAY LOL! I really wish I had a flair for this subreddit, so more people could see my cat pictures :3"],
nsa: ["Oh yeah I heard about them, they did something bad right?"],
howlie: ["ooooo howlie! I seen him here before :) he's nice. I saw him vanish into some weird subreddit a while ago, dunno what it was called, sawwy :("]},
only: {}

}},
desc: "That is a cat lady. Like most Redditors, she is between the ages of 25 and 35. She is rushing about squealing emphatically over cat pictures."};

// Sachsen (reich forums)
var sachsen = {
chat:{
invalid: "I do not wish to talk about that",
1:{

always:{
greeting: ["Heil, comrade. What brings you to the <em>Reich Forums</em>?"],
"reich forums": ["That is this place, comrade. Here we discuss our political ideologies and how they apply to our modern world."],
koj: ["Ah yes we do have a member by that name, but he has not been here in some time. I'm afraid I do not know his wearabouts."],
reddit: ["Like most large internet sites reddit is hypocritical. It claims to allow the free speech of many who, by their ignorance, should not be allowed a voice. Yet it denys a voice for the strong who seek to put the weak in their place, they do this under the guise that we are hateful. I was glad when I heard of the mass suicide at reddit."],
tor: ["Ah yes I've heard of Tor, been there a few times as well. Quite possibly the only place on the internet where freedom of speech is gaurenteed. It is gaurenteed not by a central authority, or an organization. It is guarenteed merely by the nature of Tor."]},
only: {}}

},
desc: "That is Sachsen. He is a rugged looking German man in his 20's. His demeanor is firm and ruthless."
};

//Racenation (reich forums)
var racenation = {
desc: "That is RaceNation. He is a furious and proud looking individual.",
chat:{
invalid: "The fuck are you talking about?",
1: {

always:{
greeting: ["Heil Hitler, comrade. What can I do for you?"],
sachsen: ["Sachsen is a glorious member of our forums. He is highly disciplined and intelligent, I look up to him."],
koj: ["Koj was a righteous member of our forums, the spirit of Hitler is strong with that one. He was always quick to scream at the weaker races. I remember a while ago we all attacked this feminist blog, <em>she said</em>. Koj demonstrated true righteous fury at that time."],
reddit: ["Reddit was a fucking shit hole. Full of pretentious hypocrites. I was glad to see it fall."],
"reich forums": ["These forums are a font of valuable knowledge. Truly intelligent discourse happens here and it is my hope that our intelligence will eventually spread to the weak idiots on the rest of the internet."],
feminist: ["Fucking feminists are the worst. I mean women are practically equel to men, though they shouldn't be, and they still just whine and bitch about fucking nothing. They're just complete fucking idiots."]
 },
only: {
"she said": ["She Said is a disgusting <em>feminist</em> blog, pathetic creatures. It is hosted on the blogger site, I can give you the link if you wish to hate on them, my comrade. I can't imagine any reaction to that drivel other than righteous hate.<br/><strong>You have recieved a link! You can now travel to the She Said blog from Blogger.</strong>", function(){shesaidAREA.discovered = true; return "delthis";}]}

}
}};

//Old Man
var oldman = {
chat: {
invalid: "The fuck you talkin' about?",
quest: {
redditquest: ["Hehehe-hrk, that was just fantastic, friend. Does my old heart good to see some people humbled by their own stupidity. Here, this is a link to an unlisted subreddit the NSA uses. Try and keep it underwraps, mhm? See yeh round, pal.<br/><strong>You received a link! You can now travel to the NSA subreddit.</strong>", function(){nsasubredditAREA.discovered = true; 
currentloc.contain.splice(currentloc.contain.indexOf(oldmanNPC), 1); oldmanNPC.quest = false;}]},
0: {

always: {
greeting: ["What do you want? Stupid <em>Redditor</em> ass-hats bothering me, hrgh."],
tor: ["Tor? Hehe, going slumming through the tor networks are we? Tor is like the bad parts of China town: great for the uh, discerning customer, but dangerous if you don't know your way around. Careful out there, kid, hrk"]},
only: {
redditors: ["Bunch of idiots if you ask me, hrk. Look at 'em laughing at all this stupid shit. These aren't even <em>memes</em>, they're shitty image macros at best. I'd like to show 'em something <em>really funny</em>."],
memes:  ["Back in my day the internet had real memes. Inside jokes that you had to be a part of the culture to get. This is just fucking shitty jokes pasted on top of images of dumb shit, grgh. Idiots."],
"really funny": ["Yeah, I wanna fuck with these internet-wannabe faggots. If you can figure out a prank to pull on these meme-gawking retards, then I'll try and help you out with whatever you got going on."],
nsa: ["Meh, anyone surprised by Snowden's leaks is an idiot. It's not much of a challenge to figure out the NSA, and all intelligence agencies, gather big data. Knowledge is power after all. If you're looking for them on Reddit though I might be able to help, you'll have to do something <em>really funny</em> for me first though."],
howlie: ["Whossat? One of your friends? Well I don't really care, I'm busy grumbling, hrk, about these Reddit faggots."]}

}},
desc: "It's an old man. He is balding and his face looks grizzled. He is looking at everyone scornfully and with hate in his eyes."};

//Tor Guard
var torguard = {
chat: {
invalid: "I will not talk about that.",
0: {

always: {
greeting: ["Halt, you do not possess the correct vegetable to access the Tor Network. Please move along, internetter."],
nsa: ["The NSA has no power here."],
tor: ["Tor is a network in the deep-web. It is only for those of a deviant and discerning taste."]},
only: {}

}
},
desc: "That is a Tor Guard. He is burly and covered in body armor. He is holding an Internet Stun Ray."};

//Lilithium
var lilithium = {
desc: "That is Lilithium. Like most extreme feminists, she is perpetually infuriated and full of hate. She is the author of the She Said blog.",
chat: {
invalid: "Sorry I'm not ignorant enough to talk about that.",
quest: {
feministpost: ["Hah, perfect. I bet that pissed them off. Okay I guess I trust you even for a, well I can't really tell what gender you are. So you could be a man, but also a woman. Well I trust you I suppose. Here's a link to my blog's archives, there should be some feminist uniforms in there, you can have one. Oh and don't mind our prisoner tied up in the back, haha.<br/><strong>You received a link! You can now travel to the She Said archives!</strong>", function(){shesaidarchiveAREA.discovered = true; lilithiumNPC.quest = false;}]},
1:{

always: {
greeting: ["Uh, hi. Do you need something? I'm busy informing the ignorant internet about the fight for women's rights."],
"reich forums": ["Ugh I hate those fucking fascists. They come here every now and then and spew their small-minded hate everywhere. They are fucking awful."],
koj: ["Uh why are you asking about him? You think I know anything about that ignorant nazi? Well I don't so stop asking about him."],
"she said": ["I write this blog because I'm sick of women being degraded and persecuted by chauvinistic fascists every day of their lives. It's pathetic this is still an issue in our modern society."]},
only: {
favor: ["Hmmm well I suppose you could help. Just don't fuck this up okay? Take this message I wrote and post it on the Reich Forums, that'll show those fascists.<br/><strong>You have recieved an item!</strong>", function(){inv.push(feministpostITEM); return "delthis";}]}

}
}};

//Shaleena
var shaleena = {
desc: "That is Shaleena. She is a slightly less than radical feminist, but still full of hate.",
chat: {
invalid: "Uh, like, what are you talking about?",
1:{

always:{
greeting: ["Uhm, like, hi. What are you doing here?"],
koj: ["Oh he's that stupid asshole that we- I mean he's from the Nazi forums right? Those assholes come here all the time and post rude shit. It's pathetic really."],
"reich forums": ["Ugh I hate those chauvinists. They're so simple-minded and stupid. Actually, you should ask Lilithium about a <em>favor</em> she needs done. We're banned from the forums but you aren't, so you could help!"]},
only: {}

}
}};

//--Items--
var dildo = {
desc:"It's a large pink dildo. It both unsettles and excites you."
};
var joint = {
desc:"It is a marijuana cigarette. It smells like good weed smells."};
var catpicture = {
desc:"It is a picture of a cat. The cat is doing a dance."};
var catflair = {
desc:"That's a flair for the Cats subreddit. Flairs usually indicate trusted users."};
var todayilearnedflair = {
desc:"That's a flair for the todayilearned subreddit. Flairs usually indicate trusted users."};
var redditpost = {
desc:"It's an inflammatory Reddit post that you wrote. It asserts that Reddit is no different from a Chan."};
var naziflag = {
desc: "That is a Nazi Flag, beloved by members of the Reich Forums."};
var lighter = {
desc: "It is a zippo lighter. It has a paw print on it."};
var feministpost = {
desc: "It is a passive-aggressive and inflammatory post written by Lilithium. It targets the members of the Reich Forums."};
var feministuniform = {
desc: "That is a feminist uniform. All the cool girls are wearing it. It is pink."};
var keyoj = {
desc: "It is a blood-stained key. It presumably unlocks koj's shackles."};

//--Locations--
//The Stream
var stream = {
desc:"You are at 420streams. It is a black room with television screens embedded in the wall at seemingly random places. There are empty bottles of alcohol and drug paraphernalia scattered about."
};
//Reddit
var reddit = {
desc:"You are at Reddit. It is like 4chan used to be, but more pretentious. There are hipsters wandering in and out of edgy subreddits. The air is thick with a feeling of superiority.",
altdesc: "You are at Reddit. It is like 4chan used to be, but more pretentious. There are several dead hipsters on the ground, they appear to have killed themselves."
};
var memes = {
desc: "This is a subreddit about memes. A bunch of young adults gather around five year old memes that weren't even that funny originally. The young adults are laughing hysterically.",
altdesc: "This is a subreddit about memes. It is currently deserted, there is obscene graffiti posted on the walls."};
var todayilearned = {
desc: "This is the subreddit for Today I Learned. There are bulletins of unique stories pinned around the walls. People gather around these bulletins and arbitrarily remark on what the bulletin says about humanity and their faith in it.",
altdesc: "This is the subreddit for Today I learned. There are bulletins of unique stories pinned around the walls. The ground is littered with the bodies of Redditors who committed suicide after having their self-importance challenged."};
var cats = {
desc: "This is a subreddit for cat pictures. There are lots of pictures of cats. Women between 25 and 35 are wandering around screaming, laughing, and awww'ing at pictures of cats.",
altdesc: "This is a subreddit for cat pictures. There are lots of pictures of cats. Oddly enough, the users of the cats subreddit have failed to notice the mass suicide of other Redditors. Truly cats are the only eternal thing on the internet."};
var nsasubreddit = {
desc: "This is a secret NSA Subreddit. It is a dismal, gray place filled with cubicles. The only ambience is provided by an old, sluggish ceiling fan slowly spinning around."};

//Internet Backbone
var backbone = {
desc:"You are at the Internet Backbone. This area connects many subnetworks together and acts as an internet hub. There are people hurriedly rushing about, this is a busy place."
};

//Tor
var torgate = {
desc:"This is the Tor Gate. It is the entrance to the shadowy Tor Network. It is a network known for anonymity, illegal services, and childporn. In short: it is the ultimate internet blackmarket."};
var silkroad = {
desc: "This is the Silkroad. It is a blackmarket on Tor where one can buy drugs, child porn, and hire hitmen. There are shady characters shuffling around everywhere, most are wearing trench coats."};

//Reich Forums
var reich = {
desc: "This is the Reich Forums. It is a place where Nazis, socialists, and racists gather to talk about things."};

//Blogger
var blogger = {
desc: "This is Blogger. It hosts blogs of many different topics and authors. However, the vast majority of blogs can best be described as self-important."};
var shesaid = {
desc: "This is a blog named She Said. It is an extremist feminist blog. The air is thick with estrogen."};
var shesaidarchive = {
desc: "This is the archives for the She Said blog. It is full of old and arrogant feminist blog posts."};


/*novelty commands to add:
get aids
suicide
*/
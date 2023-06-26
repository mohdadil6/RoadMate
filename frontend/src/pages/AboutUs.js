import './App.css'
import { Icon } from '@iconify/react';

const AboutUs = () => {
    return (<>
        <h3>Creators</h3>
        <br/><br/>
        <div class="toolbar-share-icon">
		<ul>
        <li><h4>Mohd Adil   <a href="https://wa.me/8359900835"><Icon icon="bi:whatsapp"/></a>
        <a href="tel:8359900835"><Icon icon="material-symbols:phone-iphone" /></a>
        <a href="https://www.instagram.com/__adil___1/"><Icon icon="mdi:instagram" /></a>
        <a href="https://www.linkedin.com/in/aadil611/"><Icon icon="mdi:linkedin" /></a><a href="https://portfolio-adil.web.app/">Portfolio</a></h4>
        </li>
        
		<li><h4>Ankit Tiwari   <a href="https://wa.me/9981670785"><Icon icon="bi:whatsapp"/></a>
        <a href="tel:9981670785"><Icon icon="material-symbols:phone-iphone" /></a>
        <a href="https://www.instagram.com/i_.m_.ankit/"><Icon icon="mdi:instagram" /></a>
        <a href="https://www.linkedin.com/in/ankit-tiwari-3a0b0090/"><Icon icon="mdi:linkedin" /></a></h4></li>

        <li><h4>Ashutosh Pandey   <a href="https://wa.me/7869999649"><Icon icon="bi:whatsapp"/></a>
        <a href="tel:7869999649"><Icon icon="material-symbols:phone-iphone" /></a>
        <a href="https://www.instagram.com/"><Icon icon="mdi:instagram" /></a>
        <a href="https://www.linkedin.com/"><Icon icon="mdi:linkedin" /></a></h4></li>

        <li><h4>Yash Mehra   <a href="https://wa.me/8305545898"><Icon icon="bi:whatsapp"/></a>
        <a href="tel:8305545898"><Icon icon="material-symbols:phone-iphone" /></a>
        <a href="https://www.instagram.com/yashmehraa/"><Icon icon="mdi:instagram" /></a>
        <a href="https://www.linkedin.com/in/yash-mehra-946958261/"><Icon icon="mdi:linkedin" /></a></h4></li>

		</ul>
		</div>
    </>);
}

export default AboutUs;
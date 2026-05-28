package com.example.data

// Domain Models for BCM GROUP Real Estate UI
data class PropertyCategory(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val iconRes: String, // String identifier or index
    val imageUrl: String
)

data class CityLocation(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val iconChar: String, // Initial character for circular icon styling
    val imageUrl: String
)

data class BcmProject(
    val id: String,
    val name: String,
    val category: String, // Plots, Villas, Flats
    val location: String, // Pali, Jalore, Sumerpur, Sheoganj, Sojat, Marwar Jn., Takhatgarh, Somesar
    val imageUrl: String,
    val size: String, // sq. ft.
    val price: String, // e.g. "₹25 Lakhs onwards" / "₹25 लाख से शुरू"
    val priceEn: String,
    val priceHi: String,
    val statusEn: String, // RERA Approved, Ready to Construct, Gated Community, Under Construction
    val statusHi: String,
    val descriptionEn: String,
    val descriptionHi: String
)

data class SlidingBanner(
    val id: Int,
    val imageUrl: String,
    val titleEn: String,
    val titleHi: String,
    val subtitleEn: String,
    val subtitleHi: String,
    val categoryId: String
)

object BcmProjectDataProvider {
    val categories = listOf(
        PropertyCategory(
            id = "plots",
            nameEn = "Plots",
            nameHi = "प्लॉट्स (भूखंड)",
            iconRes = "landscape",
            imageUrl = "https://images.unsplash.com/photo-1500382017468-9049fed747ef?w=400&auto=format&fit=crop&q=60" // Gated land/meadow
        ),
        PropertyCategory(
            id = "villas",
            nameEn = "Villas",
            nameHi = "विला (कोठी)",
            iconRes = "villa",
            imageUrl = "https://images.unsplash.com/photo-1613977257363-707ba9348227?w=400&auto=format&fit=crop&q=60" // Premium modern white villa
        ),
        PropertyCategory(
            id = "flats",
            nameEn = "Flats",
            nameHi = "फ्लैट्स (अपार्टमेंट)",
            iconRes = "apartment",
            imageUrl = "https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?w=400&auto=format&fit=crop&q=60" // Modern glass tower flats
        )
    )

    val locations = listOf(
        CityLocation("Pali", "Pali", "पाली", "P", "https://images.unsplash.com/photo-1596176530529-78163a4f7af2?w=400&auto=format&fit=crop&q=60"),
        CityLocation("Jalore", "Jalore", "जालौर", "J", "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?w=400&auto=format&fit=crop&q=60"),
        CityLocation("Sumerpur", "Sumerpur", "सुमेरपुर", "S", "https://images.unsplash.com/photo-1605276374104-dee2a0ed3cd6?w=400&auto=format&fit=crop&q=60"),
        CityLocation("Sheoganj", "Sheoganj", "शिवगंज", "S", "https://images.unsplash.com/photo-1570129477492-45c003edd2be?w=400&auto=format&fit=crop&q=60"),
        CityLocation("Sojat", "Sojat", "सोजत", "S", "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?w=400&auto=format&fit=crop&q=60"),
        CityLocation("Marwar", "Marwar Jn.", "मारवाड़ जंक्शन", "M", "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=400&auto=format&fit=crop&q=60"),
        CityLocation("Takhatgarh", "Takhatgarh", "तखतगढ़", "T", "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=400&auto=format&fit=crop&q=60"),
        CityLocation("Somesar", "Somesar", "सोमेसर", "S", "https://images.unsplash.com/photo-1513584684374-8bab748fbf90?w=400&auto=format&fit=crop&q=60")
    )

    val banners = listOf(
        SlidingBanner(
            id = 1,
            imageUrl = "https://images.unsplash.com/photo-1524813686514-a57563d77965?w=800&auto=format&fit=crop&q=80", // Gorgeous gated premium plot development landscape
            titleEn = "Premium Plots in Pali & Jalore",
            titleHi = "पाली और जालौर में प्रीमियम प्लॉट्स",
            subtitleEn = "Gated community town planning with modern lifestyle amenities.",
            subtitleHi = "आधुनिक सुख-सुविधाओं के साथ गेटेड टाउनशिप योजना।",
            categoryId = "plots"
        ),
        SlidingBanner(
            id = 2,
            imageUrl = "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=800&auto=format&fit=crop&q=80", // Splendid tall glass apartment flats
            titleEn = "Ultra-Modern Flats & Suites",
            titleHi = "अति-आधुनिक फ्लैट्स और सुइट्स",
            subtitleEn = "Vrindavan Heights & BCM Aashiyana – defining class, luxury, and comfort.",
            subtitleHi = "वृंदावन हाइट्स और बीसीएम आशियाना – वर्ग, विलासिता और आराम।",
            categoryId = "flats"
        ),
        SlidingBanner(
            id = 3,
            imageUrl = "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=800&auto=format&fit=crop&q=80", // Luxury modern Spanish style real estate villa
            titleEn = "Royal Signature Villas",
            titleHi = "शाही सिग्नेचर विला (कोठी)",
            subtitleEn = "Indulge in absolute luxury, private pools, and bespoke high ceilings.",
            subtitleHi = "पूर्ण विलासिता, निजी पूल और शानदार वास्तुकला का हिस्सा बनें।",
            categoryId = "villas"
        )
    )

    val projects = listOf(
        // Pali Projects (Pali location icon or Plot/Villa/Flat > Pali clicking)
        BcmProject(
            id = "bcm_vihar_pali",
            name = "BCM Vihar Pali",
            category = "Plots",
            location = "Pali",
            imageUrl = "https://images.unsplash.com/photo-1524813686514-a57563d77965?w=500&auto=format&fit=crop&q=70",
            size = "1200 - 3200 Sq. Ft.",
            priceEn = "₹24.5 Lakhs onwards",
            priceHi = "₹24.5 लाख से शुरू",
            price = "₹24.5 Lakhs onwards",
            statusEn = "Ready to Construct",
            statusHi = "निर्माण के लिए तैयार",
            descriptionEn = "BCM Vihar is Pali's premier integrated gated community. Featuring double lane wide asphalt roads, manicured parks, kids' play zone, 24/7 security with CCTV surveillance, underground electricity line, and high-purity water supply system.",
            descriptionHi = "बीसीसीएम विहार पाली का अग्रणी एकीकृत गेटेड समुदाय है। इसमें दोहरी लेन वाली डामर सड़कें, सुंदर पार्क, बच्चों का खेल क्षेत्र, सीसीटीवी के साथ 24/7 सुरक्षा, भूमिगत पाइप बिजली लाइन और शुद्ध जल आपूर्ति प्रणाली शामिल है।"
        ),
        BcmProject(
            id = "bcm_samriddhi",
            name = "BCM Samridhhi",
            category = "Plots",
            location = "Pali",
            imageUrl = "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?w=500&auto=format&fit=crop&q=70",
            size = "1500 - 4500 Sq. Ft.",
            priceEn = "₹35 Lakhs onwards",
            priceHi = "₹35 लाख से शुरू",
            price = "₹35 Lakhs onwards",
            statusEn = "RERA Approved Gated community",
            statusHi = "रेरा (RERA) स्वीकृत गेटेड टाउनशिप",
            descriptionEn = "A magnificent opportunity for property investors and prospective home builders. BCM Samridhhi, located on Main National Highway Jodhpur Road Pali, features a state-of-the-art Club House, Swimming Pool, and Jogging Track.",
            descriptionHi = "संपत्ति निवेशकों और संभावित घर निर्माताओं के लिए एक शानदार अवसर। मुख्य राष्ट्रीय राजमार्ग जोधपुर रोड पाली पर स्थित बीसीएम समृद्धि में अत्याधुनिक क्लब हाउस, स्विमिंग पूल और जॉगिंग ट्रैक शामिल हैं।"
        ),
        BcmProject(
            id = "bcm_residency_pali",
            name = "BCM Residency Pali",
            category = "Villas",
            location = "Pali",
            imageUrl = "https://images.unsplash.com/photo-1613977257363-707ba9348227?w=500&auto=format&fit=crop&q=70",
            size = "2100 - 4000 Sq. Ft. Double Duplex",
            priceEn = "₹78 Lakhs onwards",
            priceHi = "₹78 लाख से शुरू",
            price = "₹78 Lakhs onwards",
            statusEn = "Premium Duplex Villas",
            statusHi = "प्रीमियम डुप्लेक्स विला (कोठी)",
            descriptionEn = "A collection of architectural masterpieces tailored for those seeking privacy and royal design. Each villa features Italian marble flooring, 3 luxury Master Suites, customized kitchens, private gardens, and solar power back-up.",
            descriptionHi = "गोपनीयता और शाही वास्तुकला चाहने वालों के लिए तैयार किए गए सुरुचिपूर्ण विला। प्रत्येक विला में इतालवी संगमरमर के फर्श, 3 शानदार मास्टर सुइट्स, अनुकूलित रसोई, निजी उद्यान और सौर ऊर्जा बैकअप की सुविधा है।"
        ),
        BcmProject(
            id = "bcm_enclave_pali",
            name = "BCM Enclave",
            category = "Plots",
            location = "Pali",
            imageUrl = "https://images.unsplash.com/photo-1500382017468-9049fed747ef?w=500&auto=format&fit=crop&q=70",
            size = "800 - 2000 Sq. Ft.",
            priceEn = "₹18 Lakhs onwards",
            priceHi = "₹18 लाख से शुरू",
            price = "₹18 Lakhs onwards",
            statusEn = "Ready Possession",
            statusHi = "तुरंत कब्जा",
            descriptionEn = "Pocket-friendly residential plots inside a highly standard secured compound. Perfectly mapped and bordered with red sand-stone pillars, ready for registration and construction.",
            descriptionHi = "एक उच्च मानक सुरक्षित परिसर के भीतर बजट-अनुकूल आवासीय भूखंड। पंजीकरण और निर्माण के लिए तैयार लाल बलुआ पत्थर के स्तंभों के साथ पूर्ण सीमांकित योजना।"
        ),

        // Flats in other locations
        BcmProject(
            id = "bcm_aashiyana_pali",
            name = "BCM Aashiyana Pali",
            category = "Flats",
            location = "Pali",
            imageUrl = "https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?w=500&auto=format&fit=crop&q=70",
            size = "2 BHK & 3 BHK Premium Flats",
            priceEn = "₹32.5 Lakhs onwards",
            priceHi = "₹32.5 लाख से शुरू",
            price = "₹32.5 Lakhs onwards",
            statusEn = "Under Construction (85% Completed)",
            statusHi = "निर्माणाधीन (85% पूर्ण)",
            descriptionEn = "High-rise modern flat complex featuring beautifully designed apartments, earthquake-resistant design, automatic glass elevators, 100% power generator backup, community center, and children's activity room.",
            descriptionHi = "खूबसूरत अपार्टमेंट, भूकंप प्रतिरोधी डिजाइन, स्वचालित कांच लिफ्ट, 100% पावर जनरेटर बैकअप, सामुदायिक केंद्र और बच्चों की गतिविधि कक्ष की विशेषता वाला हाई-राइज आधुनिक फ्लैट कॉम्प्लेक्स।"
        ),
        BcmProject(
            id = "vrindavan_heights_sheoganj",
            name = "Vrindavan Heights Sheoganj",
            category = "Flats",
            location = "Sheoganj",
            imageUrl = "https://images.unsplash.com/photo-1567496898669-ee935f5f647a?w=500&auto=format&fit=crop&q=70",
            size = "3 BHK & 4 BHK Luxury Apartments",
            priceEn = "₹45 Lakhs onwards",
            priceHi = "₹45 लाख से शुरू",
            price = "₹45 Lakhs onwards",
            statusEn = "RERA Registered - Premium Flats",
            statusHi = "रेरा (RERA) पंजीकृत - प्रीमियम फ्लैट्स",
            descriptionEn = "Vrindavan Heights is Sheoganj's defining luxury tower. Experience grand heights with breathtaking views, double basements for absolute car parking safety, and a roof-top premium private sky lounge.",
            descriptionHi = "वृंदावन हाइट्स शिवगंज का नया लैंडमार्क है। शानदार दृश्यों, पूर्ण कार पार्किंग सुरक्षा के लिए डबल बेसमेंट और एक छत पर प्रीमियम प्राइवेट स्काई लाउंज के साथ अद्वितीय जीवन का अनुभव करें।"
        ),
        BcmProject(
            id = "bcm_aashiyana_marwar",
            name = "BCM Aashiyana Marwar",
            category = "Flats",
            location = "Marwar",
            imageUrl = "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=500&auto=format&fit=crop&q=70",
            size = "1 BHK & 2 BHK Smart Flats",
            priceEn = "₹19.8 Lakhs onwards",
            priceHi = "₹19.8 लाख से शुरू",
            price = "₹19.8 Lakhs onwards",
            statusEn = "Ready to Move",
            statusHi = "तैयार फ्लैट्स",
            descriptionEn = "Affordable, high-quality modern layouts built with premium vitrified tile floorings, solid core flush entrance doors, high-reputation plumbing fixtures, and secure compound gates.",
            descriptionHi = "प्रीमियम विट्रीफाइड टाइल फर्श, ठोस कोर फ्लश प्रवेश द्वार, उच्च प्रतिष्ठा वाले प्लंबिंग जुड़नार और सुरक्षित परिसर द्वारों के साथ निर्मित किफायती, उच्च गुणवत्ता वाले आधुनिक लेआउट।"
        ),

        // Add additional villas, plots, flats to fulfill drodown requirements in extra cities
        BcmProject(
            id = "bcm_hills_villas_sheoganj",
            name = "BCM Signature Villa Sheoganj",
            category = "Villas",
            location = "Sheoganj",
            imageUrl = "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=500&auto=format&fit=crop&q=70",
            size = "2500 Sq. Ft. Duplex",
            priceEn = "₹58 Lakhs onwards",
            priceHi = "₹58 लाख से शुरू",
            price = "₹58 Lakhs onwards",
            statusEn = "Gated Villa Community",
            statusHi = "सुरक्षित विला टाउनशिप",
            descriptionEn = "Indulge in royal design architecture at the foothills of Sheoganj hills. Secured perimeter, continuous private well water system, and custom interior design templates.",
            descriptionHi = "शिवगंज पहाड़ियों की तलहटी में शाही वास्तुकला का आनंद लें। सुरक्षित परिधि, निरंतर निजी कुएं जल प्रणाली, और कस्टम आंतरिक सज्जा डिजाइन टेम्पलेट।"
        ),
        BcmProject(
            id = "bcm_royal_jalore",
            name = "BCM Royal Villa Jalore",
            category = "Villas",
            location = "Jalore",
            imageUrl = "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?w=500&auto=format&fit=crop&q=70",
            size = "3000 Sq. Ft.",
            priceEn = "₹85 Lakhs onwards",
            priceHi = "₹85 लाख से शुरू",
            price = "₹85 Lakhs onwards",
            statusEn = "Premium Castle Edition",
            statusHi = "प्रीमियम कैसल संस्करण",
            descriptionEn = "Experience elite heritage layouts in Jalore. Premium construction with high quality marble, spacious high ceiling halls, and dedicated private parking for 3 luxury SUVs.",
            descriptionHi = "जालौर में विशिष्ट विरासत लेआउट का अनुभव करें। उच्च गुणवत्ता वाले संगमरमर के साथ प्रीमियम निर्माण, विशाल ऊंची छत वाले हॉल, और 3 लक्जरी एसयूवी के लिए समर्पित निजी पार्किंग।"
        ),
         BcmProject(
            id = "bcm_heritage_sojat",
            name = "BCM Green Villa Sojat",
            category = "Villas",
            location = "Sojat",
            imageUrl = "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=500&auto=format&fit=crop&q=70",
            size = "2800 Sq. Ft.",
            priceEn = "₹62 Lakhs onwards",
            priceHi = "₹62 लाख से शुरू",
            price = "₹62 Lakhs onwards",
            statusEn = "Gated Premium Layout",
            statusHi = "प्रीमियम गेटेड विला",
            descriptionEn = "Sojat's signature eco-villa community. Blending classic Rajasthani sandstone design blocks with green modern solar setups, rain-water harvesting systems, and premium terrace gardens.",
            descriptionHi = "सोजत का सिग्नेचर इको-विला समुदाय। पारंपरिक राजस्थानी बलुआ पत्थर के डिजाइनों को आधुनिक सौर सेटअप, वर्षा जल संचयन प्रणाली और छत के बगीचों के साथ एकीकृत करना।"
        ),

        // Plots in other cities
        BcmProject(
            id = "bcm_highway_plots_jalore",
            name = "BCM Gated Plots Jalore",
            category = "Plots",
            location = "Jalore",
            imageUrl = "https://images.unsplash.com/photo-1596176530529-78163a4f7af2?w=500&auto=format&fit=crop&q=70",
            size = "1000 - 2500 Sq. Ft.",
            priceEn = "₹15 Lakhs onwards",
            priceHi = "₹15 लाख से शुरू",
            price = "₹15 Lakhs onwards",
            statusEn = "Immediate Registration",
            statusHi = "तुरंत रजिस्ट्री",
            descriptionEn = "RERA approved spacious layout in Jalore. Fast growing standard residential locality with excellent concrete connectivity to schools and hospitals.",
            descriptionHi = "जालौर में रेरा स्वीकृत विशाल लेआउट। स्कूलों और अस्पतालों के लिए उत्कृष्ट कंक्रीट लिंक सड़कों के साथ सबसे तेजी से बढ़ता आवासीय क्षेत्र।"
        ),
        BcmProject(
            id = "bcm_plots_sumerpur",
            name = "BCM City Center Plots Sumerpur",
            category = "Plots",
            location = "Sumerpur",
            imageUrl = "https://images.unsplash.com/photo-1605276374104-dee2a0ed3cd6?w=500&auto=format&fit=crop&q=70",
            size = "1200 - 3000 Sq. Ft.",
            priceEn = "₹20 Lakhs onwards",
            priceHi = "₹20 लाख से शुरू",
            price = "₹20 Lakhs onwards",
            statusEn = "Fully Developed Township",
            statusHi = "पूर्ण विकसित टाउनशिप",
            descriptionEn = "Gated township structure featuring clean gardens, overhead concrete water tanks, underground utilities and complete boundary walls.",
            descriptionHi = "साफ-सुथरे बगीचों, कंक्रीट की पानी की टंकियों, भूमिगत बिजली लाइनों और पूर्ण चारदीवारी की विशेषता वाली गेटेड टाउनशिप योजना।"
        ),
        BcmProject(
            id = "bcm_plots_sheoganj",
            name = "BCM Samriddhi Enclave Sheoganj",
            category = "Plots",
            location = "Sheoganj",
            imageUrl = "https://images.unsplash.com/photo-1513694203232-719a280e022f?w=500&auto=format&fit=crop&q=70",
            size = "1500 - 2400 Sq. Ft.",
            priceEn = "₹21 Lakhs onwards",
            priceHi = "₹21 लाख से शुरू",
            price = "₹21 Lakhs onwards",
            statusEn = "Gated Compound",
            statusHi = "सुरक्षित गेटेड कैंपस",
            descriptionEn = "Exclusive location inside Sheoganj limits. Complete safety, wide blacktop roads, street lights, concrete drains, and kids safety park.",
            descriptionHi = "शिवगंज शहर के भीतर प्रमुख स्थान। पूर्ण सुरक्षा, चौड़ी ब्लैकटॉप डामर सड़कें, स्ट्रीट लाइट, कंक्रीट नालियां, और बच्चों के खेलने का सुरक्षित पार्क।"
        ),
        BcmProject(
            id = "bcm_plots_sojat",
            name = "BCM Heena City Sojat",
            category = "Plots",
            location = "Sojat",
            imageUrl = "https://images.unsplash.com/photo-1595115252273-04774cbcda74?w=500&auto=format&fit=crop&q=70",
            size = "1000 - 3200 Sq. Ft.",
            priceEn = "₹12.5 Lakhs onwards",
            priceHi = "₹12.5 लाख से शुरू",
            price = "₹12.5 Lakhs onwards",
            statusEn = "Super Prime Location",
            statusHi = "अति-महत्वपूर्ण प्राइम लोकेशन",
            descriptionEn = "Ideally situated on National Highway inside Sojat. Best investment block for building commercial shops or luxury independent houses.",
            descriptionHi = "सोजत शहर के भीतर राष्ट्रीय राजमार्ग पर आदर्श रूप से स्थित। व्यावसायिक दुकानों या स्वतंत्र विलाओं के निर्माण के लिए सबसे बेहतरीन निवेश क्षेत्र।"
        ),
        BcmProject(
            id = "bcm_plots_marwar",
            name = "BCM Junction Plots Marwar",
            category = "Plots",
            location = "Marwar",
            imageUrl = "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?w=500&auto=format&fit=crop&q=70",
            size = "1200 - 2500 Sq. Ft.",
            priceEn = "₹14 Lakhs onwards",
            priceHi = "₹14 लाख से शुरू",
            price = "₹14 Lakhs onwards",
            statusEn = "Railway Society Approved",
            statusHi = "रेलवे सोसायटी स्वीकृत",
            descriptionEn = "Perfect regular plots adjacent to Marwar Jn. Express link road, high level water drainage system, green plantation, and commercial entry gates.",
            descriptionHi = "मारवाड़ जंक्शन एक्सप्रेस लिंक रोड के निकट नियमित भूखंड। उच्च स्तरीय जल निकासी प्रणाली, वृक्षारोपण और व्यावसायिक मुख्य द्वार।"
        ),
        BcmProject(
            id = "bcm_plots_takhatgarh",
            name = "BCM Smart City Takhatgarh",
            category = "Plots",
            location = "Takhatgarh",
            imageUrl = "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=500&auto=format&fit=crop&q=70",
            size = "1500 Sq. Ft.",
            priceEn = "₹16.5 Lakhs onwards",
            priceHi = "₹16.5 लाख से शुरू",
            price = "₹16.5 Lakhs onwards",
            statusEn = "RERA Registered Layout",
            statusHi = "रेरा (RERA) पंजीकृत टाउनशिप",
            descriptionEn = "Golden residential opportunity in Takhatgarh. Immediate registry, high ground clear sweet water availability, and fully integrated street lights.",
            descriptionHi = "तखतगढ़ में आवासीय सपना साकार करने का सुनहरा अवसर। तुरंत रजिस्ट्री, स्वच्छ व मीठे जल स्तर की उपलब्धता, और पूरी तरह से सुसज्जित स्ट्रीट लाइट्स।"
        ),
        BcmProject(
            id = "bcm_plots_somesar",
            name = "BCM Residency Somesar",
            category = "Plots",
            location = "Somesar",
            imageUrl = "https://images.unsplash.com/photo-1513584684374-8bab748fbf90?w=500&auto=format&fit=crop&q=70",
            size = "1000 - 3000 Sq. Ft.",
            priceEn = "₹11 Lakhs onwards",
            priceHi = "₹11 लाख से शुरू",
            price = "₹11 Lakhs onwards",
            statusEn = "Affordable Secure Plots",
            statusHi = "किफायती व सुरक्षित भूखंड",
            descriptionEn = "High growth investing zone in Somesar outskirts. Secure campus borders, ready electrification network, solar energy street lighting.",
            descriptionHi = "सोमेसर के बाहरी इलाके में उच्च विकास निवेश क्षेत्र। सुरक्षित कैंपस सीमाएं, विद्युत नेटवर्क, सौर ऊर्जा स्ट्रीट लाइटिंग की सुविधाएं उपलब्ध हैं।"
        )
    )
}

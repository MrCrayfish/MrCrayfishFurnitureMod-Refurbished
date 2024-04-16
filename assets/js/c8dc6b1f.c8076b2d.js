"use strict";(self.webpackChunkdocumentation=self.webpackChunkdocumentation||[]).push([[208],{6868:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>o,contentTitle:()=>a,default:()=>x,frontMatter:()=>l,metadata:()=>d,toc:()=>h});var n=r(4848),i=r(8453),c=r(1470),s=r(9365);const l={sidebar_position:8,sidebar_label:"Recycle Bin (Recycling)"},a="Recycle Bin (Recycling)",d={id:"crafttweaker/recipes/recycle_bin_recycling",title:"Recycle Bin (Recycling)",description:"The Recycling Bin is a pseudo uncrafter/item deleter. Uncrafting in the game using an items actual crafting recipes is overpowered. The Recycling Bin aims to provide a handcrafted experience that doesn't break the gameplay experience, while still providing useful items from breaking down an item. Due to it's handcrafted nature, a custom recipe has to be provided for the recycle bin in order to return items from breaking it down, it is not automatic. The base mod currently supports most vanilla blocks/items (that make sense) and blocks/items from the mod itself. By default, the recycling bin will consume all items regardless if their is a recycling recipe of not; this acts like a sort of item deleter for lack of better words.",source:"@site/docs/crafttweaker/recipes/recycle_bin_recycling.md",sourceDirName:"crafttweaker/recipes",slug:"/crafttweaker/recipes/recycle_bin_recycling",permalink:"/MrCrayfishFurnitureMod-Refurbished/docs/crafttweaker/recipes/recycle_bin_recycling",draft:!1,unlisted:!1,editUrl:"https://github.com/MrCrayfish/MrCrayfishFurnitureMod-Refurbished/tree/documentation/docs/crafttweaker/recipes/recycle_bin_recycling.md",tags:[],version:"current",sidebarPosition:8,frontMatter:{sidebar_position:8,sidebar_label:"Recycle Bin (Recycling)"},sidebar:"crafttweakerSidebar",previous:{title:"Oven (Baking)",permalink:"/MrCrayfishFurnitureMod-Refurbished/docs/crafttweaker/recipes/oven_baking"},next:{title:"Sink (Fluid Transmuting)",permalink:"/MrCrayfishFurnitureMod-Refurbished/docs/crafttweaker/recipes/sink_fluid_transmuting"}},o={},h=[{value:"Recipe Manager",id:"recipe-manager",level:2},{value:"Custom Functions",id:"custom-functions",level:2},{value:"<code>addRecipe(name, recyclable, scraps[])</code>",id:"addrecipename-recyclable-scraps",level:3},{value:"Example",id:"example",level:4},{value:"<code>addScrap(id, scrap)</code>",id:"addscrapid-scrap",level:3},{value:"Example",id:"example-1",level:4},{value:"<code>addScrap(id, scraps[])</code>",id:"addscrapid-scraps",level:3},{value:"Example",id:"example-2",level:4},{value:"<code>removeScrap(id, scrap)</code>",id:"removescrapid-scrap",level:3},{value:"Example",id:"example-3",level:4},{value:"<code>removeScrap(id, scraps[])</code>",id:"removescrapid-scraps",level:3},{value:"Example",id:"example-4",level:4},{value:"<code>replaceScrap(id, from, to)</code>",id:"replacescrapid-from-to",level:3},{value:"Example",id:"example-5",level:4},{value:"Learn More",id:"learn-more",level:2}];function u(e){const t={a:"a",code:"code",h1:"h1",h2:"h2",h3:"h3",h4:"h4",hr:"hr",p:"p",pre:"pre",strong:"strong",table:"table",tbody:"tbody",td:"td",th:"th",thead:"thead",tr:"tr",...(0,i.R)(),...e.components};return(0,n.jsxs)(n.Fragment,{children:[(0,n.jsx)(t.h1,{id:"recycle-bin-recycling",children:"Recycle Bin (Recycling)"}),"\n",(0,n.jsx)(t.p,{children:"The Recycling Bin is a pseudo uncrafter/item deleter. Uncrafting in the game using an items actual crafting recipes is overpowered. The Recycling Bin aims to provide a handcrafted experience that doesn't break the gameplay experience, while still providing useful items from breaking down an item. Due to it's handcrafted nature, a custom recipe has to be provided for the recycle bin in order to return items from breaking it down, it is not automatic. The base mod currently supports most vanilla blocks/items (that make sense) and blocks/items from the mod itself. By default, the recycling bin will consume all items regardless if their is a recycling recipe of not; this acts like a sort of item deleter for lack of better words."}),"\n",(0,n.jsx)(t.h2,{id:"recipe-manager",children:"Recipe Manager"}),"\n",(0,n.jsx)(t.p,{children:(0,n.jsx)(t.code,{children:"<recipetype:refurbished_furniture:recycle_bin_recycling>"})}),"\n",(0,n.jsx)(t.h2,{id:"custom-functions",children:"Custom Functions"}),"\n",(0,n.jsx)(t.h3,{id:"addrecipename-recyclable-scraps",children:(0,n.jsx)(t.code,{children:"addRecipe(name, recyclable, scraps[])"})}),"\n",(0,n.jsx)(t.p,{children:"Adds a new recycling recipe to the recycle bin"}),"\n",(0,n.jsxs)(t.table,{children:[(0,n.jsx)(t.thead,{children:(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Paramater"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Type"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Required"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Description"})]})}),(0,n.jsxs)(t.tbody,{children:[(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"name"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"string"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"The name of the recipe, must be unique."})]}),(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"recyclable"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:(0,n.jsx)(t.a,{href:"https://docs.blamejared.com/1.20.4/en/vanilla/api/ingredient/IIngredient",children:"IIngredient"})}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"The item that is gong to be recycled"})]}),(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"scraps"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:(0,n.jsx)(t.a,{href:"https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack",children:"IItemStack[]"})}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsxs)(t.td,{style:{textAlign:"center"},children:["The scraps (items) from breaking down the ",(0,n.jsx)(t.code,{children:"recyclable"}),". Must have at least ",(0,n.jsx)(t.code,{children:"1"})," item, and no more than ",(0,n.jsx)(t.code,{children:"9"})," items."]})]})]})]}),"\n",(0,n.jsx)(t.h4,{id:"example",children:"Example"}),"\n",(0,n.jsxs)(c.A,{children:[(0,n.jsx)(s.A,{value:"zenscript",label:"ZenScript",default:!0,children:(0,n.jsx)(t.pre,{children:(0,n.jsx)(t.code,{className:"language-ts",metastring:'title="%gamedir%/scripts/example.zs"',children:'<recipetype:refurbished_furniture:recycle_bin_recycling>.addRecipe(\n    "recycling/jukebox_scrapping",\n    <item:minecraft:jukebox>, \n    [<item:minecraft:diamond>, <item:minecraft:oak_plank> * 2]\n);\n'})})}),(0,n.jsx)(s.A,{value:"json",label:"Datapack Equivelant",children:(0,n.jsx)(t.pre,{children:(0,n.jsx)(t.code,{className:"language-json",metastring:'title="(ZIP File) \ud83e\udc62 /data/[namespace]/recipes/recycling/jukebox_scrapping.json"',children:'{\n    "type": "refurbished_furniture:recycle_bin_recycling",\n    "recyclable": {\n        "item": "minecraft:jukebox"\n    },\n    "scraps": [\n        {\n            "item": "minecraft:diamond"\n        },\n        {\n            "item": "minecraft:oak_plank",\n            "count": 2\n        }\n    ]\n} \n'})})})]}),"\n",(0,n.jsx)(t.hr,{}),"\n",(0,n.jsx)(t.h3,{id:"addscrapid-scrap",children:(0,n.jsx)(t.code,{children:"addScrap(id, scrap)"})}),"\n",(0,n.jsxs)(t.p,{children:["Adds an additional item to the scraps of the recipe with the given id. If the recipe already has ",(0,n.jsx)(t.code,{children:"9"})," scraps, this call will be ignored."]}),"\n",(0,n.jsxs)(t.table,{children:[(0,n.jsx)(t.thead,{children:(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Paramater"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Type"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Required"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Description"})]})}),(0,n.jsxs)(t.tbody,{children:[(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"id"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"string"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"The id of an existing recipe."})]}),(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"scrap"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:(0,n.jsx)(t.a,{href:"https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack",children:"IItemStack"})}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsxs)(t.td,{style:{textAlign:"center"},children:["The resulting item from cooking the ",(0,n.jsx)(t.code,{children:"ingredient"}),", can have an amount."]})]})]})]}),"\n",(0,n.jsx)(t.h4,{id:"example-1",children:"Example"}),"\n",(0,n.jsx)(c.A,{children:(0,n.jsx)(s.A,{value:"zenscript",label:"ZenScript",default:!0,children:(0,n.jsx)(t.pre,{children:(0,n.jsx)(t.code,{className:"language-ts",metastring:'title="%gamedir%/scripts/example.zs"',children:'// Add a stick to the scraps when breaking down a jukebox\n// This recipe comes from the above addRecipe() example.\n<recipetype:refurbished_furniture:recycle_bin_recycling>.addScrap(\n    "craftteaker:recycling/jukebox_scrapping",\n    <item:minecraft:stick>\n);\n'})})})}),"\n",(0,n.jsx)(t.hr,{}),"\n",(0,n.jsx)(t.h3,{id:"addscrapid-scraps",children:(0,n.jsx)(t.code,{children:"addScrap(id, scraps[])"})}),"\n",(0,n.jsxs)(t.p,{children:["Adds additional items to the scraps of the recipe with the given id. If the recipe already has ",(0,n.jsx)(t.code,{children:"9"})," scraps or the new total is greater than ",(0,n.jsx)(t.code,{children:"9"}),", this call will be ignored."]}),"\n",(0,n.jsxs)(t.table,{children:[(0,n.jsx)(t.thead,{children:(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Paramater"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Type"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Required"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Description"})]})}),(0,n.jsxs)(t.tbody,{children:[(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"id"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"string"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"The name of the recipe, must be unique."})]}),(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"scraps"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:(0,n.jsx)(t.a,{href:"https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack",children:"IItemStack[]"})}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsxs)(t.td,{style:{textAlign:"center"},children:["The resulting item from cooking the ",(0,n.jsx)(t.code,{children:"ingredient"}),", can have an amount."]})]})]})]}),"\n",(0,n.jsx)(t.h4,{id:"example-2",children:"Example"}),"\n",(0,n.jsx)(c.A,{children:(0,n.jsx)(s.A,{value:"zenscript",label:"ZenScript",default:!0,children:(0,n.jsx)(t.pre,{children:(0,n.jsx)(t.code,{className:"language-ts",metastring:'title="%gamedir%/scripts/example.zs"',children:'// Add a stick and 2 apples to the scraps when breaking down a jukebox\n// This recipe comes from the above addRecipe() example.\n<recipetype:refurbished_furniture:recycle_bin_recycling>.addScrap(\n    "craftteaker:recycling/jukebox_scrapping",\n    [<item:minecraft:stick>, <item:minecraft:apple> * 2]\n);\n'})})})}),"\n",(0,n.jsx)(t.hr,{}),"\n",(0,n.jsx)(t.h3,{id:"removescrapid-scrap",children:(0,n.jsx)(t.code,{children:"removeScrap(id, scrap)"})}),"\n",(0,n.jsx)(t.p,{children:"Remove the scrap (item) from the recipe with the given id. If the item does not exist in the scraps, this call will be ignored."}),"\n",(0,n.jsxs)(t.table,{children:[(0,n.jsx)(t.thead,{children:(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Paramater"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Type"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Required"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Description"})]})}),(0,n.jsxs)(t.tbody,{children:[(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"id"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"string"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"The name of the recipe, must be unique."})]}),(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"scrap"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:(0,n.jsx)(t.a,{href:"https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack",children:"IItemStack"})}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsxs)(t.td,{style:{textAlign:"center"},children:["The resulting item from cooking the ",(0,n.jsx)(t.code,{children:"ingredient"}),", can have an amount."]})]})]})]}),"\n",(0,n.jsx)(t.h4,{id:"example-3",children:"Example"}),"\n",(0,n.jsx)(c.A,{children:(0,n.jsx)(s.A,{value:"zenscript",label:"ZenScript",default:!0,children:(0,n.jsx)(t.pre,{children:(0,n.jsx)(t.code,{className:"language-ts",metastring:'title="%gamedir%/scripts/example.zs"',children:'// Remove diamonds from the scraps when breaking down a jukebox\n// This recipe comes from the above addRecipe() example.\n<recipetype:refurbished_furniture:recycle_bin_recycling>.addScrap(\n    "craftteaker:recycling/jukebox_scrapping",\n    <item:minecraft:diamond>\n);\n'})})})}),"\n",(0,n.jsx)(t.hr,{}),"\n",(0,n.jsx)(t.h3,{id:"removescrapid-scraps",children:(0,n.jsx)(t.code,{children:"removeScrap(id, scraps[])"})}),"\n",(0,n.jsx)(t.p,{children:"Removes the scraps (items) from the recipe with the given id. This will remove any items that match from the given scraps. Items that don't exist in the scraps of the matching recipe, will simply be ignored."}),"\n",(0,n.jsxs)(t.table,{children:[(0,n.jsx)(t.thead,{children:(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Paramater"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Type"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Required"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Description"})]})}),(0,n.jsxs)(t.tbody,{children:[(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"id"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"string"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"The name of the recipe, must be unique."})]}),(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"scraps"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:(0,n.jsx)(t.a,{href:"https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack",children:"IItemStack[]"})}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsxs)(t.td,{style:{textAlign:"center"},children:["The resulting item from cooking the ",(0,n.jsx)(t.code,{children:"ingredient"}),", can have an amount."]})]})]})]}),"\n",(0,n.jsx)(t.h4,{id:"example-4",children:"Example"}),"\n",(0,n.jsx)(c.A,{children:(0,n.jsx)(s.A,{value:"zenscript",label:"ZenScript",default:!0,children:(0,n.jsx)(t.pre,{children:(0,n.jsx)(t.code,{className:"language-ts",metastring:'title="%gamedir%/scripts/example.zs"',children:'// Remove diamond and oak planks from the scraps when breaking down a jukebox\n// This recipe comes from the above addRecipe() example.\n<recipetype:refurbished_furniture:recycle_bin_recycling>.addScrap(\n    "craftteaker:recycling/jukebox_scrapping",\n    [<item:minecraft:diamond>, <item:minecraft:oak_plank>]\n);\n'})})})}),"\n",(0,n.jsx)(t.hr,{}),"\n",(0,n.jsx)(t.h3,{id:"replacescrapid-from-to",children:(0,n.jsx)(t.code,{children:"replaceScrap(id, from, to)"})}),"\n",(0,n.jsx)(t.p,{children:"Replaces an item in the scraps of the matching recipe with a different item. If the item to replace does not exist, this call will simply be ignored."}),"\n",(0,n.jsxs)(t.table,{children:[(0,n.jsx)(t.thead,{children:(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Paramater"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Type"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Required"}),(0,n.jsx)(t.th,{style:{textAlign:"center"},children:"Description"})]})}),(0,n.jsxs)(t.tbody,{children:[(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"id"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"string"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"The name of the recipe, must be unique."})]}),(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"from"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:(0,n.jsx)(t.a,{href:"https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack",children:"IItemStack[]"})}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsxs)(t.td,{style:{textAlign:"center"},children:["The resulting item from cooking the ",(0,n.jsx)(t.code,{children:"ingredient"}),", can have an amount."]})]}),(0,n.jsxs)(t.tr,{children:[(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"to"}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:(0,n.jsx)(t.a,{href:"https://docs.blamejared.com/1.20.4/en/vanilla/api/item/IItemStack",children:"IItemStack[]"})}),(0,n.jsx)(t.td,{style:{textAlign:"center"},children:"Yes"}),(0,n.jsxs)(t.td,{style:{textAlign:"center"},children:["The resulting item from cooking the ",(0,n.jsx)(t.code,{children:"ingredient"}),", can have an amount."]})]})]})]}),"\n",(0,n.jsx)(t.h4,{id:"example-5",children:"Example"}),"\n",(0,n.jsx)(c.A,{children:(0,n.jsx)(s.A,{value:"zenscript",label:"ZenScript",default:!0,children:(0,n.jsx)(t.pre,{children:(0,n.jsx)(t.code,{className:"language-ts",metastring:'title="%gamedir%/scripts/example.zs"',children:'// Replaces diamond with an apple from the scraps when breaking down a jukebox\n// This recipe comes from the above addRecipe() example.\n<recipetype:refurbished_furniture:recycle_bin_recycling>.addScrap(\n    "craftteaker:recycling/jukebox_scrapping",\n    <item:minecraft:diamond>,\n    <item:minecraft:apple>\n);\n'})})})}),"\n",(0,n.jsx)(t.hr,{}),"\n",(0,n.jsx)(t.h2,{id:"learn-more",children:"Learn More"}),"\n",(0,n.jsxs)(t.p,{children:["See ",(0,n.jsx)(t.strong,{children:"Recipe Managers"})," on the CraftTweaker ",(0,n.jsx)(t.a,{href:"https://docs.blamejared.com/1.20.4/en/tutorial/Recipes/RecipeManagers",children:"documentation"})," for all inbuilt functions."]})]})}function x(e={}){const{wrapper:t}={...(0,i.R)(),...e.components};return t?(0,n.jsx)(t,{...e,children:(0,n.jsx)(u,{...e})}):u(e)}},9365:(e,t,r)=>{r.d(t,{A:()=>s});r(6540);var n=r(4164);const i={tabItem:"tabItem_Ymn6"};var c=r(4848);function s(e){let{children:t,hidden:r,className:s}=e;return(0,c.jsx)("div",{role:"tabpanel",className:(0,n.A)(i.tabItem,s),hidden:r,children:t})}},1470:(e,t,r)=>{r.d(t,{A:()=>A});var n=r(6540),i=r(4164),c=r(3104),s=r(6347),l=r(205),a=r(7485),d=r(1682),o=r(9466);function h(e){return n.Children.toArray(e).filter((e=>"\n"!==e)).map((e=>{if(!e||(0,n.isValidElement)(e)&&function(e){const{props:t}=e;return!!t&&"object"==typeof t&&"value"in t}(e))return e;throw new Error(`Docusaurus error: Bad <Tabs> child <${"string"==typeof e.type?e.type:e.type.name}>: all children of the <Tabs> component should be <TabItem>, and every <TabItem> should have a unique "value" prop.`)}))?.filter(Boolean)??[]}function u(e){const{values:t,children:r}=e;return(0,n.useMemo)((()=>{const e=t??function(e){return h(e).map((e=>{let{props:{value:t,label:r,attributes:n,default:i}}=e;return{value:t,label:r,attributes:n,default:i}}))}(r);return function(e){const t=(0,d.X)(e,((e,t)=>e.value===t.value));if(t.length>0)throw new Error(`Docusaurus error: Duplicate values "${t.map((e=>e.value)).join(", ")}" found in <Tabs>. Every value needs to be unique.`)}(e),e}),[t,r])}function x(e){let{value:t,tabValues:r}=e;return r.some((e=>e.value===t))}function p(e){let{queryString:t=!1,groupId:r}=e;const i=(0,s.W6)(),c=function(e){let{queryString:t=!1,groupId:r}=e;if("string"==typeof t)return t;if(!1===t)return null;if(!0===t&&!r)throw new Error('Docusaurus error: The <Tabs> component groupId prop is required if queryString=true, because this value is used as the search param name. You can also provide an explicit value such as queryString="my-search-param".');return r??null}({queryString:t,groupId:r});return[(0,a.aZ)(c),(0,n.useCallback)((e=>{if(!c)return;const t=new URLSearchParams(i.location.search);t.set(c,e),i.replace({...i.location,search:t.toString()})}),[c,i])]}function m(e){const{defaultValue:t,queryString:r=!1,groupId:i}=e,c=u(e),[s,a]=(0,n.useState)((()=>function(e){let{defaultValue:t,tabValues:r}=e;if(0===r.length)throw new Error("Docusaurus error: the <Tabs> component requires at least one <TabItem> children component");if(t){if(!x({value:t,tabValues:r}))throw new Error(`Docusaurus error: The <Tabs> has a defaultValue "${t}" but none of its children has the corresponding value. Available values are: ${r.map((e=>e.value)).join(", ")}. If you intend to show no default tab, use defaultValue={null} instead.`);return t}const n=r.find((e=>e.default))??r[0];if(!n)throw new Error("Unexpected error: 0 tabValues");return n.value}({defaultValue:t,tabValues:c}))),[d,h]=p({queryString:r,groupId:i}),[m,g]=function(e){let{groupId:t}=e;const r=function(e){return e?`docusaurus.tab.${e}`:null}(t),[i,c]=(0,o.Dv)(r);return[i,(0,n.useCallback)((e=>{r&&c.set(e)}),[r,c])]}({groupId:i}),j=(()=>{const e=d??m;return x({value:e,tabValues:c})?e:null})();(0,l.A)((()=>{j&&a(j)}),[j]);return{selectedValue:s,selectValue:(0,n.useCallback)((e=>{if(!x({value:e,tabValues:c}))throw new Error(`Can't select invalid tab value=${e}`);a(e),h(e),g(e)}),[h,g,c]),tabValues:c}}var g=r(2303);const j={tabList:"tabList__CuJ",tabItem:"tabItem_LNqP"};var f=r(4848);function y(e){let{className:t,block:r,selectedValue:n,selectValue:s,tabValues:l}=e;const a=[],{blockElementScrollPositionUntilNextRender:d}=(0,c.a_)(),o=e=>{const t=e.currentTarget,r=a.indexOf(t),i=l[r].value;i!==n&&(d(t),s(i))},h=e=>{let t=null;switch(e.key){case"Enter":o(e);break;case"ArrowRight":{const r=a.indexOf(e.currentTarget)+1;t=a[r]??a[0];break}case"ArrowLeft":{const r=a.indexOf(e.currentTarget)-1;t=a[r]??a[a.length-1];break}}t?.focus()};return(0,f.jsx)("ul",{role:"tablist","aria-orientation":"horizontal",className:(0,i.A)("tabs",{"tabs--block":r},t),children:l.map((e=>{let{value:t,label:r,attributes:c}=e;return(0,f.jsx)("li",{role:"tab",tabIndex:n===t?0:-1,"aria-selected":n===t,ref:e=>a.push(e),onKeyDown:h,onClick:o,...c,className:(0,i.A)("tabs__item",j.tabItem,c?.className,{"tabs__item--active":n===t}),children:r??t},t)}))})}function b(e){let{lazy:t,children:r,selectedValue:i}=e;const c=(Array.isArray(r)?r:[r]).filter(Boolean);if(t){const e=c.find((e=>e.props.value===i));return e?(0,n.cloneElement)(e,{className:"margin-top--md"}):null}return(0,f.jsx)("div",{className:"margin-top--md",children:c.map(((e,t)=>(0,n.cloneElement)(e,{key:t,hidden:e.props.value!==i})))})}function v(e){const t=m(e);return(0,f.jsxs)("div",{className:(0,i.A)("tabs-container",j.tabList),children:[(0,f.jsx)(y,{...e,...t}),(0,f.jsx)(b,{...e,...t})]})}function A(e){const t=(0,g.A)();return(0,f.jsx)(v,{...e,children:h(e.children)},String(t))}},8453:(e,t,r)=>{r.d(t,{R:()=>s,x:()=>l});var n=r(6540);const i={},c=n.createContext(i);function s(e){const t=n.useContext(c);return n.useMemo((function(){return"function"==typeof e?e(t):{...t,...e}}),[t,e])}function l(e){let t;return t=e.disableParentContext?"function"==typeof e.components?e.components(i):e.components||i:s(e.components),n.createElement(c.Provider,{value:t},e.children)}}}]);
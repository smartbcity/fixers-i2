import { addons } from '@storybook/addons';
import {create} from "@storybook/theming";
import logo from "../public/logo.png";

addons.setConfig({
    theme: create({
        base: 'light',
        brandTitle: 'SmartB I2',
        brandUrl: 'https://docs.smartb.city/i2',
        brandImage: logo,
    }),
});

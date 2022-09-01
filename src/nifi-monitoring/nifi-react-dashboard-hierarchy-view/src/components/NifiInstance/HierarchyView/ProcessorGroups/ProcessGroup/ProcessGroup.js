import React from 'react';
import {Button, Icon} from "antd";
import ButtonGroup from "antd/es/button/button-group";
import {Link} from "react-router-dom";
import * as endpoint from "../../../../../Endpoints";

/*Button Group which represent Nifi Process Group on Hierarchy View 1*/
const processGroup = (props) => {

    console.log(props.id);

    if (props.clicked === true)
        return (
            <ButtonGroup style={{margin: '0px 20px 0px 0px'}}>
                <Button key="1" onClick={props.addChild} type="primary"
                >{props.name}</Button>
                <Button key="2" type="danger"><Icon type="eye" theme="filled"/></Button>
                <Button key="3" type="dashed">
                    <Link
                        target="_blank"
                        to={{pathname: endpoint.LINK_TO_NIFI_INSTANCE + props.id + "&componentIds="}}>이동</Link>
                </Button>
            </ButtonGroup>
        );
    else
        return (
            <ButtonGroup style={{margin: '0px 20px 0px 0px'}}>
                <Button key="1" onClick={props.addChild} type="default"
                >{props.name}</Button>
                <Button key="2" type="danger"><Icon type="eye" theme="filled"/></Button>
                <Button key="3" type="dashed">
                    <Link
                        target="_blank"
                        to={{pathname: endpoint.LINK_TO_NIFI_INSTANCE + props.id +"&componentIds="}}>이동</Link>
                </Button>
            </ButtonGroup>
        );
};

export default processGroup;

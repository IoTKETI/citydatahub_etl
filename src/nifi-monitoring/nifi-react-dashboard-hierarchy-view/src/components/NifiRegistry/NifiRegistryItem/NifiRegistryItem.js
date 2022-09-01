import React, {Component} from 'react';
import * as tableColumns from "../../../TableColumns";
import {Spin, Table} from "antd";

import axios from "../../../axios-spring-boot-api-instance";
import * as endpoint from "../../../Endpoints";


class NifiRegistryItem extends Component {

    state = {
        items: [],
        loading: true,
    };

    componentDidMount() {
        axios.get(endpoint.GET_ALL_ITEMS)
            .then(res => {
                this.setState({items: res.data.data});
                console.log(res.data.data);
                this.setState({loading: false})
            });
    }

    render() {

        let show = <Spin style={{paddingTop: '50%'}} tip="Loading..."> </Spin>;

        if (this.state.items && this.state.loading === false) {
            let itemData = this.state.items.map(item => {
                return {
                    "bucketIdentifier": item.bucketIdentifier,
                    "bucketName": item.bucketName,
                    "createdTimestamp": item.createdTimestamp,
                    "modifiedTimestamp": item.modifiedTimestamp,
                    "description": item.description,
                    "identifier": item.identifier,
                    "name": item.name,
                    "type": item.type,
                    "versionCount": item.versionCount,
                }
            });

            show = (
                <div style={{padding: 24, background: '#fff', minHeight: 360}}>
                    <Table
                        size='small' columns={tableColumns.itemColumns}
                        dataSource={itemData} bordered={true} scroll={{x: 2000}}/>
                </div>

            );
        }

        return show;
    }
}

export default NifiRegistryItem;

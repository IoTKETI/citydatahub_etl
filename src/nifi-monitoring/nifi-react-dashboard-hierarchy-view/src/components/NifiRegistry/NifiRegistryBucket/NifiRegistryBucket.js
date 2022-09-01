import React, {Component} from 'react';
import * as tableColumns from "../../../TableColumns";
import {Spin, Table} from "antd";

import axios from "../../../axios-spring-boot-api-instance";
import * as endpoint from "../../../Endpoints";


class NifiRegistryBucket extends Component {

    state = {
        buckets: [],
        loading: true,
    };

    componentDidMount() {
        axios.get(endpoint.GET_ALL_BUCKETS)
            .then(res => {
                this.setState({buckets: res.data.data});
                console.log(res.data.data);
                this.setState({loading: false})
            });
    }

    render() {

        let show = <Spin style={{paddingTop: '50%'}} tip="Loading..."> </Spin>;

        if (this.state.buckets && this.state.loading === false) {
            let bucketData = this.state.buckets.map(bucket => {
                return {
                    allowBundleRedeploy: bucket.allowBundleRedeploy,
                    allowPublicRead: bucket.allowPublicRead,
                    createdTimestamp: bucket.createdTimestamp,
                    identifier: bucket.identifier,
                    name: bucket.name
                }

            });

            show = (
                <div style={{padding: 24, background: '#fff', minHeight: 360}}>
                    <Table
                        size='small' columns={tableColumns.bucketColumns}
                        dataSource={bucketData} bordered={true}/>
                </div>

            );
        }

        return show;
    }
}

export default NifiRegistryBucket;
